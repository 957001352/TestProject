package com.dhlk.utils;

public class Cmd{

    public static void execCommand(String[] arstringCommand) {
        for (int i = 0; i < arstringCommand.length; i++) {
            System.out.print(arstringCommand[i] + " ");
        }
        try {
            Runtime.getRuntime().exec(arstringCommand);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void execCommand(String arstringCommand) {
        try {
            Process exec = Runtime.getRuntime().exec(arstringCommand);
            System.out.println(exec.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void cmd(){
        //打开记算器
        String[] arstringCommand = new String[] {
                "cmd ",
                "/k",
                "start", // cmd Shell命令
                "calc"
        };
        execCommand(arstringCommand);
        //打开记事本
        String cmd = "cmd /k start notepad";
        execCommand(cmd);
    }
}
