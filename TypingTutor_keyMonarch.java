import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
class TutorWorking{
    static class ProgressTracker{
        int timesProgressSaved;
        boolean[] toAddSpace = new boolean[10];

        ArrayList<Integer> listProgress = new ArrayList<>(3);
        ArrayList<Integer> listProgressWPM = new ArrayList<>(3);
        void progressGraphPrinter(){
            StringBuffer[] a = new StringBuffer[10];
            for (int i = 0; i < 10; i++) {
                a[i] = new StringBuffer();
            }

            for (int i = 1; i < 10; i++) {
                a[i].append((char)(i+65)).append(" class -| ");
            }
            a[0].append("\t   0 *------------------------------------> Time");

            for (Integer progress : listProgress) {
                graphPlotter(a, progress);
            }

            graphPrinter(a);

        }
        private void graphPrinter(StringBuffer[] sbToPrint){
            if(listProgress.isEmpty()) System.out.println("Please Save Your Progress !");
            else {
                for (int i = 9; i >= 0; i--) {
                    System.out.println(sbToPrint[i]);
                }
            }

        }
        private void graphPlotter(StringBuffer[] a , int x){
            a[x].append(" ".repeat(timesProgressSaved)).append("*");
            timesProgressSaved++;
        }
        void addProgress(int iGetProgress){
            listProgress.add(iGetProgress);
            System.out.println("Progress Saved Successfully!!");
        }

        void showProgress(){
            System.out.println("---------- Progress Chart ----------");

            if(listProgress.isEmpty()){
                System.out.println("Please Save Your Progress");
            }else {
                for (int i = 0; i < listProgress.size(); i++) {
                    System.out.println((i+1)+".Entry\t\t\t\t  Adjusted WPM "+(listProgressWPM.get(i)));
                }
            }
        }
    }


    ProgressTracker progressTracker = new ProgressTracker();

    void displayTips(){
        System.out.println("---------------- TIPS SECTION ----------------");
        System.out.println("""
                1. Use the correct starting position. When practicing your typing skills,
                   it's important to use proper hand placement.
                """);

        System.out.println("2. Maintain good posture and do not look Down your hands\n");
        System.out.println("3. Keep at least 45 - 70 cm of distance between your eyes and the screen\n");
        System.out.println("4. Face the screen with your head slightly tilted forward\n");
    }
    void speedChecker()  {
        Scanner sc = new Scanner(System.in);

        Random rand = new Random();
        String[] sPara = new String[3];

        sPara[0] = "Amidst the cosmic symphony, Earth's vibrant stories unfold. From serene landscapes to bustling cities,\n" +
                "human threads weave tales of triumph and tribulation, contributing to the grand narrative of existence.";

        sPara[1] = "Beneath the celestial canopy, Earth spins tales of resilience and beauty. Nature's wonders and\n" +
                "human endeavors intertwine, creating a narrative of diversity and harmony in the grand cosmic tableau.";

        sPara[2] = "In the cosmic theater, Earth plays its part with breathtaking landscapes and human stories.\n" +
                "From sunlit meadows to urban skylines, diverse experiences merge into a captivating narrative across the cosmic stage.";

        System.out.println("Please Be Ready For a Quick Speed Test .....\n"+"+".repeat(45)+"\n");

        try {
            Thread.sleep(1500);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int iRand = rand.nextInt(3);
        System.out.println(sPara[iRand]);
        System.out.println("\n"+"+".repeat(45));

        UserInterfaceTutor.printMsgWithProgressBar("Loading Speed Test : Press Enter To Submit the Test");
        System.out.println("\n\t\t\tPlease Start Typing");

        long startTime = System.currentTimeMillis();
        String sUserInput = sc.nextLine();
        long endTime = System.currentTimeMillis();

        int[] iArrData = getWords(sPara, sUserInput, iRand);

        int iWords = iArrData[2];
        int iLettersPos = iArrData[3];
        int iLettersNeg = iArrData[0];
        int iTotalLetters = iArrData[1];

        float WPM = ((float) iWords * 60 ) / ((endTime-startTime)/1000F);
        int ACCURACY = (iLettersPos*100)/(iTotalLetters);

        System.out.println("Your Typing Speed Comes Near About "+(WPM)+" WPM");
        System.out.println("Your Accuracy is "+(ACCURACY)+"%");
        System.out.println("Your Adjusted Typing Speed is "+((WPM * ACCURACY)/100)+"\n\n");

        System.out.println("Do you want to SAVE your Progress ? (y/n)");
        String sInput = sc.next().trim();

        if(sInput.equalsIgnoreCase("Y")){
            int toCompare = pointCalculator((int)((WPM * ACCURACY)/100));
            progressTracker.addProgress(toCompare);
            progressTracker.listProgressWPM.add((int)((WPM * ACCURACY)/100));
        }

        if(WPM < 18){
            System.out.println("Based on your Typing Speed Your Experience comes to be Beginner\n");
        }
        else if(WPM < 35){
            System.out.println("Based on your Typing Speed Your Experience comes to be Basic\n");
        }
        else if(WPM < 60){
            System.out.println("Based on your Typing Speed Your Experience comes to be Intermediate\n");
        }
        else{
            System.out.println("Based on your Typing Speed Your Experience comes to be Advance\n");
        }
    }

    private int pointCalculator(int WPM){
        if(WPM < 15) return 1;
        else if(WPM < 30) return 2;
        else if(WPM < 45) return 3;
        else if(WPM < 60) return 4;
        else if(WPM < 75) return 5;
        else if(WPM < 90) return 6;
        else if(WPM < 105) return 7;
        else if(WPM < 120) return 8;
        else return 9;
    }
    private int[] getWords(String[] sPara, String sUserInput, int iRand) {
        String[][] sParaArr = {sPara[0].split(" ") , sPara[1].split(" ") , sPara[2].split(" ")};

        String[] sInputArr = sUserInput.split(" ");

        int iWords = 0;
        int iLettersPos = 0;
        int iLettersNeg = 0;
        int iTotalLetters = 0;

        int j;

        for (int i = 0; i < sInputArr.length; i++) {

            int minToUse = Math.min(sInputArr[i].length(),sParaArr[iRand][i].length());

            for (j = 0; j < minToUse; j++) {
                if(sInputArr[i].charAt(j) == sParaArr[iRand][i].charAt(j)){
                    iLettersPos++;
                    iTotalLetters++;
                }else{
                    iLettersNeg++;
                    iTotalLetters++;
                }
            }
            if (j == sInputArr[i].length()){
                if (iLettersPos > iLettersNeg){
                    iWords++;
                }
            }
        }
        return new int[]{iLettersNeg , iTotalLetters , iWords, iLettersPos};
    }


}

class UserInterfaceTutor{
    static void welcomePage(){
        Scanner sc = new Scanner(System.in);
        logoPrinter();

        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\t\tWelcome to KEYmonarch : A User friendly Java based Tutor to teach Touch Typing\n");

        boolean toProceed = true;
        TutorWorking tFunc = new TutorWorking();

        while(toProceed){
            int iInput = getiInput(sc);

            switch(iInput){

                case 1 -> tFunc.speedChecker();
                case 2 -> tFunc.displayTips();
                case 3 -> tFunc.progressTracker.progressGraphPrinter();
                case 4 -> tFunc.progressTracker.showProgress();
                case 5 -> toProceed = false;
            }
        }
    }

    private static int getiInput(Scanner sc) {
        System.out.println("++++++++ : Please Select From The menu : +++++++++");
        System.out.println("1. Typing Test \t\t3. progress Tracking Graph");
        System.out.println("2. Typing Tips \t\t4. Progress");
        System.out.println("5. EXIT...");
        System.out.println("----------------------------------------------");

        System.out.print("Your Choice : ");
        return sc.nextInt();
    }

    static void logoPrinter(){
        String[] str2 = {
                "   __     __ ________ __      __                                                           __",
                "   |  \\  /  \\        \\  \\    /  \\                                                         |  \\",
                "   | ▓▓ /  ▓▓ ▓▓▓▓▓▓▓▓\\▓▓\\  /  ▓▓______ ____   ______  _______   ______   ______   _______| ▓▓____",
                "   | ▓▓/  ▓▓| ▓▓__     \\▓▓\\/  ▓▓|      \\    \\ /      \\|       \\ |      \\ /      \\ /       \\ ▓▓    \\",
                "   | ▓▓  ▓▓ | ▓▓  \\     \\▓▓  ▓▓ | ▓▓▓▓▓▓\\▓▓▓▓\\  ▓▓▓▓▓▓\\ ▓▓▓▓▓▓▓\\ \\▓▓▓▓▓▓\\  ▓▓▓▓▓▓\\  ▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓\\",
                "   | ▓▓▓▓▓\\ | ▓▓▓▓▓      \\▓▓▓▓  | ▓▓ | ▓▓ | ▓▓ ▓▓  | ▓▓ ▓▓  | ▓▓/      ▓▓ ▓▓   \\▓▓ ▓▓     | ▓▓  | ▓▓",
                "   | ▓▓ \\▓▓\\| ▓▓_____    | ▓▓   | ▓▓ | ▓▓ | ▓▓ ▓▓__/ ▓▓ ▓▓  | ▓▓  ▓▓▓▓▓▓▓ ▓▓     | ▓▓_____| ▓▓  | ▓▓",
                "   | ▓▓  \\▓▓\\ ▓▓     \\   | ▓▓   | ▓▓ | ▓▓ | ▓▓\\▓▓    ▓▓ ▓▓  | ▓▓\\▓▓    ▓▓ ▓▓      \\▓▓     \\ ▓▓  | ▓▓",
                "   \\▓▓   \\▓▓\\▓▓▓▓▓▓▓▓    \\▓▓    \\▓▓  \\▓▓  \\▓▓ \\▓▓▓▓▓▓ \\▓▓   \\▓▓ \\▓▓▓▓▓▓▓\\▓▓       \\▓▓▓▓▓▓▓\\▓▓   \\▓▓\n",
                "========================================================================================================",
                "========================================================================================================"
        };
        for (String lToPrint : str2){
            System.out.println(lToPrint);
        }
    }
    static void printMsgWithProgressBar(String sMsg){
        int totalTasks = 100;
        int currentTask = 0;
        int progressBarWidth = 41;

        System.out.println(sMsg);

        StringBuffer progressStringBuffer = new StringBuffer();
        progressStringBuffer.append(" ".repeat(progressBarWidth));

        while (currentTask <= totalTasks) {
            int progress = (int) (currentTask / (float) totalTasks * progressBarWidth);
            StringBuffer progressBarBuffer = new StringBuffer(progressBarWidth + 10);

            progressBarBuffer.append('[');

            for (int i = 0; i < progressBarWidth; i++) {
                if (i < progress) {
                    progressBarBuffer.append('=');
                } else if (i == progress) {
                    progressBarBuffer.append('>');
                } else {
                    progressBarBuffer.append(' ');
                }
            }

            progressBarBuffer.append("] ").append(progress * 100 / progressBarWidth).append('%');
            String progressBarWithBuffer = progressBarBuffer.toString();

            System.out.print("\r" + progressBarWithBuffer);

            try {
                Thread.sleep(50);
            } catch (Exception e) {
                e.printStackTrace();
            }
            currentTask++;
        }
    }
}

public class TypingTutorProject {
    public static void main(String[] args) {

        TutorWorking.ProgressTracker p = new TutorWorking.ProgressTracker();
        UserInterfaceTutor.welcomePage();
    }
}
