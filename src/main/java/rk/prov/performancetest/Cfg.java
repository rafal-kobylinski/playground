package rk.prov.performancetest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Cfg
{
    @Value("${mq.hostname}")
    private String mqHost;
    @Value("${mq.channel}")
    private String mqChannel;
    @Value("${mq.port}")
    private int mqPort;
    @Value("${mq.queue}")
    private String mqQueue;
    @Value("${mq.queuemanager}")
    private String mqQueueManager;

    public String getMqHost() {
        return mqHost;
    }
    public String getMqChannel() {
        return mqChannel;
    }
    public int getMqPort() {
        return mqPort;
    }
    public String getMqQueue() {
        return mqQueue;
    }
    public String getMqQueueManager() {
        return mqQueueManager;
    }
}
