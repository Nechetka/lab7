package com.nechet.common.util.console;

public class UniversalConsole implements BaseConsole{
    BaseConsole console;
    public UniversalConsole(BaseConsole console){
        this.console=console;
    }

    @Override
    public void print(String str) {
        console.print(str);
    }

    @Override
    public void printLine(String str) {
        console.printLine(str);
    }

    @Override
    public String read() {
        return console.read();
    }
}
