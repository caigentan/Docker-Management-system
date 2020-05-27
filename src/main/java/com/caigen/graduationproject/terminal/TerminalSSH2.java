package com.caigen.graduationproject.terminal;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author  yasin
 * @date  2020-03-21 22:14
 * @version 1.0
 */


public class TerminalSSH2 {

    //GetYmlLinuxInfoBean getYmlLinuxInfoBean = new GetYmlLinuxInfoBean();
    private String hostname = "192.168.134.128";
    private String username = "root";
    private String password = "admin";

    public String createContainerBackId(String command){
        String containerId = null;
        Connection conn = new Connection(hostname);
        Session ssh = null;
        try {
            conn.connect();
            boolean isConn = conn.authenticateWithPassword(username,password);
            if(!isConn){
                return "eroror，可能是密码错误";
            }else{
                ssh = conn.openSession();
                ssh.execCommand(command);
                InputStream is = new StreamGobbler(ssh.getStdout());
                BufferedReader brs = new BufferedReader(new InputStreamReader(is));
                containerId = brs.readLine();
                System.out.println(containerId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //连接的Session和Connection对象都需要关闭
            ssh.close();
            conn.close();
        }
        return containerId;
    }
}
