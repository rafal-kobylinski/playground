package rk.prov.performancetest.connectors;

import com.jcraft.jsch.*;

import java.io.InputStream;

public class SshClient {

    public SshClient(){}

    public String executeCommand(String command){

        String output = "";
        try{
            JSch jsch=new JSch();
            jsch.addIdentity("");
            String user="";
            String host="";
            Session session=jsch.getSession(user, host, 22);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();

            Channel channel=session.openChannel("exec");
            //String command = ". ~/.profile; mp 602382393 |grep ^[0-9]";
            ((ChannelExec)channel).setCommand(command);

            channel.setInputStream(null);
            //channel.setOutputStream(null);
            //((ChannelExec)channel).setErrStream(System.err);

            InputStream in=channel.getInputStream();
            channel.connect();

            byte[] tmp=new byte[1024];
            while(true){
                while(in.available()>0){
                    int i=in.read(tmp, 0, 1024);
                    if(i<0)break;
                    //System.out.print(new String(tmp, 0, i));
                    output += new String(tmp, 0, i);

                }
                if(channel.isClosed()){
                    if(in.available()>0) continue;
                    //System.out.println("exit-status: "+channel.getExitStatus());
                    break;
                }
                try{
                    Thread.sleep(1000);}catch(Exception ee){}
            }
            channel.disconnect();
            session.disconnect();
        }
        catch(Exception e){
            System.out.println(e);
        }

        return output;
    }

}
