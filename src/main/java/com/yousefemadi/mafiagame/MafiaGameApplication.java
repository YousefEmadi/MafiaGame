package com.yousefemadi.mafiagame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;




@SpringBootApplication
public class MafiaGameApplication {

    public static Game createNewGame(int code) {
        Scenario scenario = new Scenario(code);
        ArrayList<Player> players = new ArrayList<>();

        for(int i = 0; i < scenario.roles.size(); ++i) {
            players.add(i, new Player(i + 1, scenario.roles.get(i)));
        }

        Collections.shuffle(players);
        String[] names = new String[]{"AAA", "BBB", "CCC", "DDD", "EEE", "FFF", "GGG", "HHH", "III", "JJJ"};

        for(int i = 0; i < players.size(); ++i) {
            (players.get(i)).name = names[i];
        }

        return new Game(100, players, scenario);
    }
    public static boolean passGameVoteCriteria(Player player, Game game) {
        int criteria = 0;
        int voters = game.players.size();
        if ((voters <= 15) && (voters >= 13)) 
            criteria = 6;
        if ((voters <= 12) && (voters >= 11))
            criteria = 5;
        if (criteria == 5 && game.maxReceivedVotes() >= 8)   // too many votes for someone
            criteria = 6;
        if ((voters <= 10) && (voters >= 8))
            criteria = 4;
        if (criteria == 4 && game.maxReceivedVotes() >= 7)   // too many votes for someone
            criteria = 5;
        if ((voters <= 7) && (voters >= 6))
            criteria = 3;
        if ((voters <= 5) && (voters >= 4))
            criteria = 2;
        return (player.receivedVotes >= criteria);
    }


    public static void main(String[] args) {
        SpringApplication.run(MafiaGameApplication.class, args);


        Scanner input = new Scanner(System.in);
        Game game = createNewGame(10);
        ArrayList<Player> defendantsPlayers = new ArrayList();
        ArrayList<Player> lynchList = new ArrayList();
        ArrayList<Player> playersWithMaxLynchVote = new ArrayList();
        ArrayList<Player> playersWithMaxBadRecord = new ArrayList();

        while(true) {
            System.out.println("1. Start the game");
            System.out.println("2. End Game");
            System.out.println("3. Show Game");
            System.out.println("4. Start Voting");
            System.out.println("5. Show Voting result");
            System.out.println("6. Start Lynch");
            System.out.println("7. Show Lynch result");
            System.out.println("8. Reset Voting Module");
            System.out.println("9. Show Alive players");
            System.out.println("0. Exit");
            System.out.print("Enter a command: ");
            int command = input.nextInt();

            try {
                switch (command) {
                    case 0:
                        System.exit(0);
                    default:
                        System.out.println("Invalid command");
                        break;
                    case 1:
                        game.start();
                        break;
                    case 2:
                        game.stop();
                        break;
                    case 3:
                        System.out.println(game);
                        break;
                    case 4:
                        defendantsPlayers.clear();
                        lynchList.clear();
                        playersWithMaxLynchVote.clear();
                        playersWithMaxBadRecord.clear();
                        System.out.println("Voting Starts with " + game.players.size() + " players");
                        game.players.forEach((player) -> {
                            System.out.println(player.name + ": ");
                            player.receivedVotes = input.nextInt();
                            if (passGameVoteCriteria(player, game)) {
                                ++player.badRecord;
                                defendantsPlayers.add(player);
                            }

                        });
                        System.out.println("defendantsPlayers\n" + defendantsPlayers);
                        break;
                    case 5:
                        System.out.println("defendantsPlayers\n:" + defendantsPlayers);
                        break;
                    case 6:

                        if (defendantsPlayers.size() == 2) {
                            defendantsPlayers.forEach((player) -> {
                                player.canVote = false;
                            });
                        };  // if there are 2 defendants, they can't vote for lynching each other
                        int lynchVoters = (int) game.players.stream().filter(player -> player.isAlive && player.canVote).count();


                        System.out.println("Lynching Starts with " + lynchVoters + " players as lynch voters and " + defendantsPlayers.size() + " defendantsPlayers");
                        defendantsPlayers.forEach((defendant) -> {
                            System.out.println(defendant.name + ": ");
                            defendant.receivedVotes = input.nextInt();
                            if (passGameVoteCriteria(defendant, game)) {
                                lynchList.add(defendant);
                            }

                        });
                        Collections.sort(lynchList);
                        Collections.reverse(lynchList);
                        System.out.println("lynchList" + lynchList);
//                        int maxReceivedVotes = (lynchList.get(0)).receivedVotes;
//                        for(int i = 0; i < lynchList.size(); ++i) {
//                            if ((lynchList.get(i)).receivedVotes >= maxReceivedVotes) {
//                                maxReceivedVotes = (lynchList.get(i)).receivedVotes;
//                            }
//                        }

                        System.out.println("maxReceivedVote number = " + game.maxReceivedVotes());
                        lynchList.forEach((player) -> {
                            if (player.receivedVotes == game.maxReceivedVotes()) {
                                playersWithMaxLynchVote.add(player);
                            }

                        });
                        if (playersWithMaxLynchVote.size() == 1) {
                            (playersWithMaxLynchVote.get(0)).die();
                            game.players.remove(playersWithMaxLynchVote.get(0));
                            System.out.println("playersWithMaxLynchVote and Lynched by citizens" + playersWithMaxLynchVote);
                        } else {
                            int maxBadRecord = (playersWithMaxLynchVote.get(0)).badRecord;

                            for (Player player : playersWithMaxLynchVote) {
                                if (player.badRecord >= maxBadRecord) {
                                    maxBadRecord = player.badRecord;
                                }
                            }

                            System.out.println("maxBadRecord number = " + maxBadRecord);
                            int finalMaxBadRecord = maxBadRecord;
                            playersWithMaxLynchVote.forEach((player) -> {
                                if (player.badRecord == finalMaxBadRecord) {
                                    playersWithMaxBadRecord.add(player);
                                }

                            });
                            System.out.println("playersWithMaxBadRecord" + playersWithMaxBadRecord);
                            if (playersWithMaxBadRecord.size() == 1) {
                                (playersWithMaxBadRecord.get(0)).die();
                                game.removePlayer(playersWithMaxBadRecord.get(0));
                                System.out.println("playersWithMaxLynchVote and Lynched by citizens" + playersWithMaxBadRecord);
                            } else {
                                System.out.println("No one to lynch");
                            }
                        }
                        break;
                    case 7:
                        System.out.println("lynchList" + lynchList);
                        break;
                    case 8:
                        defendantsPlayers.clear();
                        lynchList.clear();
                        playersWithMaxLynchVote.clear();
                        break;
                    case 9:
                        System.out.println(game.players.size() + " players in the game");
                        game.players.forEach((player) -> {
                            if (player.isAlive) {
                                System.out.println(player.name + " is alive");
                            }

                        });
                }
            } catch (Exception var12) {
                System.out.println("Invalid command");
            }
        }

    }

}
