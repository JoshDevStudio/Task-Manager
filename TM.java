import java.io.*;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.lang.String;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.NumberFormat;
import java.text.DecimalFormat;

//Eyasu Haile
//CSC 131
//Prof Posnett
//Task Manager Class

public class TM {
	public static void timer(String nameofTask) {

		try {
			File file = new File("stamptime.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			String startline;

			while ((line = br.readLine()) != null) {
				String[] delims = line.split(" ");

				String firstIn = delims[0];
				String secondIn = delims[1];
				String selectedTask = firstIn + " " + secondIn;

				String day = delims[4];
				String timing = delims[5];
				String date = day + " " + timing;

				String startTimer;
				String stopTimer;
				if (selectedTask.equals(nameofTask + " Start")) {

					startTimer = date;
					//System.out.println(startTimer);
					while ((line = br.readLine()) != null) {

						String[] delims2 = line.split(" ");

						if ((delims2[0] + " " + delims2[1]).equals(nameofTask + " Stop")) {
							//System.out.println("PASSED IT");
							stopTimer = delims2[4] + " " + delims2[5];
							//System.out.println(stopTimer);
							// CALC TIMER
							String dateStart = startTimer;
							String dateStop = stopTimer;

							// HH converts hour in 24 hours format (0-23), day calculation
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

							Date d1 = null;
							Date d2 = null;

							try {
								d1 = format.parse(dateStart);
								d2 = format.parse(dateStop);

								// in milliseconds
								long diff = d2.getTime() - d1.getTime();
								// PRINT BY SECONDS
								//System.out.println(diff);

								long diffSeconds = diff / 1000 % 60;
								long diffMinutes = diff / (60 * 1000) % 60;
								long diffHours = diff / (60 * 60 * 1000) % 24;
								long diffDays = diff / (24 * 60 * 60 * 1000);
								
								NumberFormat formatter = new DecimalFormat("00");  
								String hours = formatter.format(diffHours); 
								String minutes = formatter.format(diffMinutes); 
								String seconds = formatter.format(diffSeconds); 

								String totaledTime = hours + ":" + minutes + ":" + seconds;
								//System.out.println("Total Time for Task: " + totaledTime);
								// System.out.print(diffDays + " days, ");
								
								// WRITE TO TIMEDATA TIMER LINE
								try {
									File file2 = new File("timedata.txt");
									BufferedReader reader = new BufferedReader(new FileReader(file2));
									String line1 = "", oldtext1 = "";
									while ((line1 = reader.readLine()) != null) {
										oldtext1 += line1 + "\r\n";
									}

									reader.close();
									String newtext = oldtext1.replaceAll(nameofTask + " Timer -",
											nameofTask + " Timer - " + totaledTime);

									FileWriter writer = new FileWriter("timedata.txt");
									writer.write(newtext);
									writer.close();

								} catch (IOException ioe) {
									ioe.printStackTrace();
								}

							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					}
				}
			}

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public static void startTask(String taskName) {
		try {

			String verify, putData;
			File file = new File("timedata.txt");

			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write("\n");
			bw.write("Task Name: " + taskName + "\n");

			bw.write(taskName + " Description -" + "\n");
			bw.write(taskName + " Timer - " + "\n");
			bw.write(taskName + " Size - " + "\n");

			bw.flush();
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			File file = new File("stamptime.txt");
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date startDate = new Date();
			String strStartDate = dateFormat.format(startDate);

			bw.write(taskName + " Start Time - " + strStartDate + "\n");

			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			File file = new File("taskSizeData.txt");
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write("");
			//bw.flush();
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void stopTask(String taskName) {
		try {
			File file = new File("stamptime.txt");
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date stopDate = new Date();
			String strStopDate = dateFormat.format(stopDate);

			bw.write(taskName + " Stop Time - " + strStopDate + "\n");
			bw.flush();
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		timer(taskName);
	}
	
	
	public static void describeTask(String taskName, String taskDescription) {
		try {
			File file = new File("timedata.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "", oldtext = "";
			
			while ((line = reader.readLine()) != null) {
				oldtext += line + "\r\n";
			}
			reader.close();
			
			String newtext = oldtext.replaceAll(taskName + " Description -",
					taskName + " Description - " + taskDescription + " / ");

			FileWriter writer = new FileWriter("timedata.txt");
			writer.write(newtext);
			writer.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		try {
			File file = new File("stamptime.txt");
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date describeDate = new Date();
			String strdescribeDate = dateFormat.format(describeDate);

			bw.write(taskName + " Describe Time - " + strdescribeDate + "\n");
			bw.flush();
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void sizeTask(String taskName, String shirtsize) {
		try {
			File file = new File("timedata.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "", oldtext = "";
			
			while ((line = reader.readLine()) != null) {
				oldtext += line + "\r\n";
			}
			reader.close();
			String newtext = oldtext.replaceAll(taskName + " Size -",
					taskName + " Size - " + shirtsize + " / ");

			FileWriter writer = new FileWriter("timedata.txt");
			writer.write(newtext);
			writer.close();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		// *************
		try {
			File file = new File("stamptime.txt");
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date sizeDate = new Date();
			String strsizeDate = dateFormat.format(sizeDate);

			bw.write(taskName + " Size Time - " + strsizeDate + "\n");
			bw.flush();
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// **********
		try {
			File file = new File("taskSizeData.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			
			int sizeCounter = 0;
			while ((line = reader.readLine()) != null) {
				if (line.equals(shirtsize)) {
					
					sizeCounter = sizeCounter + 1;
				}
			}

			System.out.println(sizeCounter);
			
			if (sizeCounter == 0) {
				try {
					File file1 = new File("taskSizeData.txt");
					FileWriter fw = new FileWriter(file1, true);
					BufferedWriter bw = new BufferedWriter(fw);

					bw.write("\n");
					bw.write(shirtsize);
					bw.flush();
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			reader.close();
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public static void taskSummary(String taskName) {
		try {
			File file = new File("timedata.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String line;
			String startline;
			while ((line = br.readLine()) != null) {
				String[] delims = line.split(" "); 
				
				if (delims[0].equals(taskName) || (line.equals("Task Name: " + taskName))) {
					System.out.println(line);
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			File file = new File("stamptime.txt");
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date tasksummaryDate = new Date();
			String strtasksummaryDate = dateFormat.format(tasksummaryDate);

			bw.write(taskName + " Summary Time - " + strtasksummaryDate + "\n");
			bw.flush();
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void summary(String taskCommand) {
		try {
			File file = new File("timedata.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			File file = new File("stamptime.txt");
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date summaryDate = new Date();
			String strsummaryDate = dateFormat.format(summaryDate);

			String exSummary = "  --  ";
			
			bw.write(exSummary + " Summary Time - " + strsummaryDate + "\n");
			bw.flush();
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		//stats();
		taskNamesForSize();
	}
	
	
	
	public static void taskNamesForSize() {
		taskSizes();
		//
		minTimeForSize();
		maxTimeForSize();
		avgTimeForSize();
	}
	
	public static void taskSizes() {
		try {
			File file = new File("taskSizeData.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String line;
			System.out.println("\nALL Task Sizes:  ");
		
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}	
	
	public static void minTimeForSize() {
		int prevTime = 30000000;
		int nextSec;
		String maxname;
		
		try {
			File file = new File("taskSizeData.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String line;
			System.out.println("\n\n** MINIMUM TIME on each Task Sizes **");
			
			while ((line = br.readLine()) != null) {
				
				System.out.println("For Task Size: " + line);
				
				//System.out.println(" \n");
				
				try {
					
					File file1 = new File("timedata.txt");
					FileReader reader = new FileReader(file1);
					BufferedReader breader = new BufferedReader(reader);

					String line1;
					//System.out.println("THIS IS WHAT IS FOUND: ");
					while ((line1 = breader.readLine()) != null) {
						String[] delims = line1.split(" "); 
						
						if (delims.length > 3) {
				        	if (delims[1].equals("Size")) {
				        		if (delims[3].equals(line)) {
				        			// printed from timedata
				        			// System.out.println(delims[3] + " OK");
				        			// use delims[0]
				        			try {
				    					File file2 = new File("timedata.txt");
				    					FileReader reader2 = new FileReader(file2);
				    					BufferedReader breader2 = new BufferedReader(reader2);

				    					String line2;
				    					while ((line2 = breader2.readLine()) != null) {
				    						String[] delims2 = line2.split(" "); 
				    						
				    						
				    						if ( (delims2[0].equals(delims[0])) && (delims2[1].equals("Timer"))) {
				    							String zeroMark = "00:00:00";
				    							SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
				    							Date d1 = null;
				    							Date d2 = null;
				    							try {
				    								d1 = format.parse(zeroMark);
				    								// Timer from timedata
				    								d2 = format.parse(delims2[3]);
				    								
				    								long diff = d2.getTime() - d1.getTime();
				    								nextSec = (int) diff;
				    								if (prevTime < nextSec) {
				    								
				    									//System.out.println(prevTime);
				    								} else {
				    									prevTime = nextSec;
				    									//System.out.println(delims[0]);
				    									System.out.println(delims2[0]);
				    									
				    									//System.out.println(prevTime);
				    									
				    								}
				    								//System.out.println(sec);
				    								
				    							} catch (Exception e) {
				    								e.printStackTrace();
				    							}
				    							
				    						}
				    					}
				    					//System.out.println(prevTime);
				    					// close third READER2
				    					breader2.close();
				    					
				    				} catch (IOException e) {
				    					e.printStackTrace();
				    				}
				        			
				        		}
				        	}
						}
				        	
					}

					// close secondary READER
					breader.close();
				//System.out.println(sec);
					
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				//This shows the final Max number
				System.out.print("    ^----This is MIN time for task");
				
				System.out.println(" - Time taken: " + prevTime + " Milliseconds");
				System.out.println("    ");
				
				//System.out.println(line);
				
			}

			// initial READER
			br.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void maxTimeForSize() {
		
		int prevTime = 0;
		int nextSec;
		String maxname;
		
		try {
			File file = new File("taskSizeData.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String line;
			System.out.println("\n\n** MAXIMUM TIME on each Task Sizes **");
			
			while ((line = br.readLine()) != null) {
				
				System.out.println("For Task Size: " + line);
			
				try {
					
					File file1 = new File("timedata.txt");
					FileReader reader = new FileReader(file1);
					BufferedReader breader = new BufferedReader(reader);

					String line1;
					//System.out.println("THIS IS WHAT IS FOUND: ");
					while ((line1 = breader.readLine()) != null) {
						String[] delims = line1.split(" "); 
						
						if (delims.length > 3) {
				        	if (delims[1].equals("Size")) {
				        		if (delims[3].equals(line)) {
				        			// printed from timedata
				        			// System.out.println(delims[3] + " OK");
				        			// use delims[0]
				        			try {
				    					File file2 = new File("timedata.txt");
				    					FileReader reader2 = new FileReader(file2);
				    					BufferedReader breader2 = new BufferedReader(reader2);

				    					String line2;
				    					while ((line2 = breader2.readLine()) != null) {
				    						String[] delims2 = line2.split(" "); 
				    						
				    						if ( (delims2[0].equals(delims[0])) && (delims2[1].equals("Timer"))) {
				    							String zeroMark = "00:00:00";
				    							SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
				    							Date d1 = null;
				    							Date d2 = null;
				    							try {
				    								d1 = format.parse(zeroMark);
				    								// Timer from timedata
				    								d2 = format.parse(delims2[3]);
				    								
				    								long diff = d2.getTime() - d1.getTime();
				    								nextSec = (int) diff;
				    								if (prevTime > nextSec) {
				    								
				    									//System.out.println(prevTime);
				    								} else {
				    									prevTime = nextSec;
				    									//System.out.println(delims[0]);
				    									System.out.println(delims2[0]);
				    									
				    									//System.out.println(prevTime);
				    									
				    								}
				    								//System.out.println(sec);
				    								
				    							} catch (Exception e) {
				    								e.printStackTrace();
				    							}
				    						}
				    					}
				    					//System.out.println(prevTime);
				    					// close third READER2
				    					breader2.close();
				    					
				    				} catch (IOException e) {
				    					e.printStackTrace();
				    				}
				        			
				        		}
				        	}
						}
					}

					// close secondary READER
					breader.close();
				//System.out.println(sec);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				//This shows the final Max number
				System.out.print("    ^----This is MAX time for task");
				
				System.out.println(" - Time taken: " + prevTime + " Milliseconds");
				System.out.println("    ");
				
			}

			// initial READER
			br.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void avgTimeForSize() {
		
		int nextSec;
		String maxname;
		
		try {
			File file = new File("taskSizeData.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String line;
			System.out.println("\n\n** AVERAGE on each Task Sizes **");
			
			while ((line = br.readLine()) != null) {
				
				System.out.println("For Task Size: " + line);
				int prevTime = 0;
				int avgNum = 0;
				try {
					
					File file1 = new File("timedata.txt");
					FileReader reader = new FileReader(file1);
					BufferedReader breader = new BufferedReader(reader);

					String line1;
					//System.out.println("THIS IS WHAT IS FOUND: ");
					while ((line1 = breader.readLine()) != null) {
						String[] delims = line1.split(" "); 
						
						if (delims.length > 3) {
				        	if (delims[1].equals("Size")) {
				        		if (delims[3].equals(line)) {
				        			// printed from timedata
				        			// System.out.println(delims[3] + " OK");
				        			// use delims[0]
				        			
				        			try {
				    					File file2 = new File("timedata.txt");
				    					FileReader reader2 = new FileReader(file2);
				    					BufferedReader breader2 = new BufferedReader(reader2);

				    					String line2;
				    					while ((line2 = breader2.readLine()) != null) {
				    						String[] delims2 = line2.split(" "); 
				    						
				    						
				    						if ( (delims2[0].equals(delims[0])) && (delims2[1].equals("Timer"))) {
				    							String zeroMark = "00:00:00";
				    							SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
				    							Date d1 = null;
				    							Date d2 = null;
				    							try {
				    								d1 = format.parse(zeroMark);
				    								// Timer from timedata
				    								d2 = format.parse(delims2[3]);
				    								
				    								long diff = d2.getTime() - d1.getTime();
				    								nextSec = (int) diff;
				    								avgNum++;
				    								prevTime = prevTime + nextSec;
				    								System.out.println(delims2[0]);
				    								
				    							} catch (Exception e) {
				    								e.printStackTrace();
				    							}
				    							
				    						}
				    					}
				    					
				    					// close third READER2
				    					breader2.close();
				    					
				    				} catch (IOException e) {
				    					e.printStackTrace();
				    				}
				        			
				        		}
				        	}
						}
				        	
					}

					// close secondary READER
					breader.close();
				//System.out.println(sec);
					
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				//This shows the final Max number
				System.out.print("    ^----The Task(s) ");
				
				System.out.println(" - have AVERAGE time of: " + prevTime + " Milliseconds ");
				System.out.println("    ");
				System.out.println(avgNum);
			}

			// initial READER
			br.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static void renameTask(String oldName, String newName) {
		
		try {
			File file = new File("timedata.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "", oldtext = "";
			
			while ((line = reader.readLine()) != null) {
				oldtext += line + "\r\n";
			}
			reader.close();
			
			
			String newtext = oldtext.replaceAll(oldName, newName);
			FileWriter writer = new FileWriter("timedata.txt");
			
			writer.write(newtext);
			writer.close();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		////////////////////////////////
		try {
			File file = new File("stamptime.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "", oldtext = "";
			
			while ((line = reader.readLine()) != null) {
				oldtext += line + "\r\n";
			}
			reader.close();
			
			String newtext = oldtext.replaceAll(oldName, newName);
			FileWriter writer = new FileWriter("stamptime.txt");
			
			writer.write(newtext);
			writer.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static void deleteTask(String taskName) {
		try {
			File file = new File("timedata.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "", oldtext = "", deleteLine = "";
			
			while ((line = reader.readLine()) != null) {
				oldtext += line + "\r\n";
			}
			reader.close();
			//String[] delims = line.split(" ");
			String newtext = oldtext.replaceAll("Task Name: " + taskName, "");
			FileWriter writer = new FileWriter("timedata.txt");
			
			writer.write(newtext);
			writer.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
				
		try {
			File file = new File("timedata.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "", oldtext = "", deleteLine = "";
			
			while ((line = reader.readLine()) != null) {
				oldtext += line + "\r\n";
			}
			reader.close();
			
			String newtext = oldtext.replaceAll(taskName + " Description -", "");
			FileWriter writer = new FileWriter("timedata.txt");
			
			writer.write(newtext);
			writer.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		try {
			File file = new File("timedata.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "", oldtext = "", deleteLine = "";
			
			while ((line = reader.readLine()) != null) {
				oldtext += line + "\r\n";
			}
			reader.close();
			
			String newtext = oldtext.replaceAll(taskName + " Timer -", "");
			FileWriter writer = new FileWriter("timedata.txt");
			
			writer.write(newtext);
			writer.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		try {
			File file = new File("timedata.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "", oldtext = "", deleteLine = "";
			
			while ((line = reader.readLine()) != null) {
				oldtext += line + "\r\n";
			}
			reader.close();
			
			String newtext = oldtext.replaceAll(taskName + " Size -", "");
			FileWriter writer = new FileWriter("timedata.txt");
			
			writer.write(newtext);
			writer.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static void log(String taskCommand) {
		
		try {
			File file = new File("stamptime.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		try {
			File file = new File("stamptime.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			
			while ((line = br.readLine()) != null) {
				String[] delims = line.split(" "); 
				
				String date = delims[4];
				String time = delims[5];
				String name = delims[0];
				// IF Action is DESCRIBE, then add the "   " description
				String action = delims[1];
				
				String logData = date + " " + time + " " + name + " " + action;
				System.out.println(logData);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}

	public static void main(String[] args) {
		//
		switch (args[0]) {
			case "start":
				startTask(args[1]);
				break;
			case "stop":
				stopTask(args[1]);
				break;
			case "describe":
				describeTask(args[1], args[2]);
				break;
			case "summary":
				if (args.length > 1) {
					taskSummary(args[1]);
				} else {
					summary(args[0]);
				}
				break;
			case "log":
				log(args[0]);
				break;
			case "size":
				sizeTask(args[1], args[2]);
				break;
			case "rename":
				renameTask(args[1], args[2]);
				break;
			case "delete":
				deleteTask(args[1]);
			default:
				break;
		}
	}
}
