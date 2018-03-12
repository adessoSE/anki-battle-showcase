package de.adesso.anki.battle.transfer;

public class CommandDTO {
    private String command;
    private String arg0;
    private String arg1;
    private String arg2;
    private String arg3;
    private String arg4;

    @Override
    public String toString(){
        return command + " " + arg0 + " " + arg1 + " " + arg2 + " " + arg3 + " " + arg4;
    }

    public String getCommand() {
        return command;
    }
    public void setCommand(String command) {
        this.command = command;
    }
    public String getArg0() {
        return arg0;
    }
    public void setArg0(String arg0) {
        this.arg0 = arg0;
    }
    public String getArg1() {
        return arg1;
    }
    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }
    public String getArg2() {
        return arg2;
    }
    public void setArg2(String arg2) {
        this.arg2 = arg2;
    }
    public String getArg3() {
        return arg3;
    }
    public void setArg3(String arg3) {
        this.arg3 = arg3;
    }
    public String getArg4() {
        return arg4;
    }
    public void setArg4(String arg4) {
        this.arg4 = arg4;
    }


}
