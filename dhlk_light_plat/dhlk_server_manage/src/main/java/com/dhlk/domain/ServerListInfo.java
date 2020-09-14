package com.dhlk.domain;

import lombok.Data;

@Data
public class ServerListInfo implements Comparable<ServerListInfo> {
    private String command;
    private String cpuUsage;
    private String user;
    private String mem;
    private String vsz;
    private String rss;
    private String start;
    private String time;
    public ServerListInfo() {
    }

    public ServerListInfo(String cpuUsage, String user, String mem, String vsz, String rss, String start, String time) {
        this.cpuUsage = cpuUsage;
        this.user = user;
        this.mem = mem;
        this.vsz = vsz;
        this.rss = rss;
        this.start = start;
        this.time = time;
    }

    @Override
    public int compareTo(ServerListInfo o) {

        int i = (int) ((Float.parseFloat(o.getCpuUsage().replace("%", "")) * 100) - (Float.parseFloat(this.getCpuUsage().replace("%", "")) * 100));
        return i;
    }
}
