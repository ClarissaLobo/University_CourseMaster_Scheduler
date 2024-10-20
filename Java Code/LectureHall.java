import java.util.*;

import javax.lang.model.util.ElementScanner14;

import java.io.*;
public class LectureHall {
    ArrayList<String> tresult = new ArrayList<String>(10);
    static ArrayList<String> tresult1 = new ArrayList<String>(10);
    ArrayList<String> tresult2 = new ArrayList<String>(10);
    List<Integer> Starttime = new ArrayList<Integer>(10);
    List<Integer> Finishtime = new ArrayList<Integer>(10);
    ArrayList<Integer> lecture = new ArrayList<Integer>(10);
    List<Integer> FinalList = new ArrayList<Integer>(10);
    //private String str3;

    public void readinputFile(String Lectures) {
        try {
            FileReader fr = new FileReader(Lectures);
            try (BufferedReader br = new BufferedReader(fr)) {
                String cpre = br.readLine();
                while (cpre != null) {
                    String r = cpre;
                    r = r.replaceAll("[:^]", " ");
                    String[] str1 = r.split(" ");
                    tresult.addAll(Arrays.asList(str1));
                    Active(tresult);
                    tresult.clear();
                    cpre = br.readLine();
                }

                br.close();
                HallAssigned(Starttime, Finishtime, lecture);
                Assign(lecture, tresult1, FinalList);
                // System.out.println(tresult1);
                // System.out.println(FinalList);
                CreateHallAssigned(tresult1,FinalList);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        catch (FileNotFoundException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.out.println("File issue" + e);
        }
    }

    public void Active(ArrayList<String> A) {
        for (int i = 0; i < A.size(); i++) {
            if (i == 0) {
                tresult1.add(A.get(i));
            } else if (i == 1) {
                tresult2.add(A.get(i));
            } else if (i == 2) {
                Starttime.add(Integer.parseInt(A.get(i)));
            } else if (i == 3) {
                Finishtime.add(Integer.parseInt(A.get(i)));
            }
        }
        // System.out.println(tresult1);
        // System.out.println(tresult2);
        // System.out.println(Starttime);
        // System.out.println(Finishtime);
        

    }

    public static void HallAssigned(List<Integer> S, List<Integer> F, ArrayList<Integer> L) {

        int i, j;
        System.out.print("Following lectures are selected to be alotted : ");
        i = 0;
        System.out.print(i + " ");
        L.add(i);
        

        // Consider rest of the activities
        for (j = 1; j <S.size(); j++) {
            if (S.get(j) >= F.get(i)) {
                System.out.print(j + " ");
                L.add(j);
                
                
                // System.out.println("hi");
                i = j;
            }
            else{
                //L.add(0);
            }
        }
        System.out.println(L);
        //S.add(0);
    }

    public void Assign(ArrayList<Integer> Le, ArrayList<String> T,List<Integer> F){
            int flag=0;
            for(int i=0;i<T.size(); i++){
                flag=0;
                for(int j=0;j<Le.size();j++){
                    if(i==Le.get(j))
                    {  
                        flag=1;
                        //F.add(1);
                        
                        break;
                    }
                }
                if(flag==1)
                {
                      F.add(1);
                }
                else{
                    F.add(0);
        
                }
                }
    // System.out.println(F);

    }

    // Create CreateHallAssigned File
    public void CreateHallAssigned(ArrayList<String> A,List<Integer> F) {
        
        //System.out.println(A);
        //System.out.println(F);
        int flag=0;
        try {

            File myObj = new File("CourseHallAssignedOutput.txt");
            if (!myObj.exists()) {

                myObj.createNewFile();
                System.out.println("File created: " + myObj.getName());
            }
            else {
                for(int i=0;i<A.size() && i<F.size();i++){
                    String str3=A.get(i);
                    int lec=F.get(i);
                    WriteToFile(str3,lec);
                 }
                
            }
        } catch (IOException e) {
            System.out.println("issue" + e);
        }
    }

    public static void WriteToFile(String str3,Integer lec) {
        // List<String> outValues = new ArrayList<>();
        try (FileWriter f = new FileWriter("CourseHallAssignedOutput.txt", true);
                BufferedWriter b = new BufferedWriter(f);
                PrintWriter p = new PrintWriter(b);) {
            p.println("CourseName:" + " " +str3 );
            if (lec==1){
            p.println("LectureHall:" + " " +lec );
            }
            else{
                p.println("LectureHall:" + " " +lec );
            }
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
 }