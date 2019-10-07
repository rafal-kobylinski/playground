package rk.prov.performancetest.connectors;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rk.prov.performancetest.Cfg;

import java.io.IOException;

@Service
public final class MqClient
{
    private MQQueueManager queueManager;
    private MQQueue outQueue;
    private Cfg cfg;

    @Autowired
    public MqClient(Cfg cfg)
    {
        this.cfg = cfg;
        MQEnvironment.hostname = cfg.getMqHost();
        MQEnvironment.channel = cfg.getMqChannel();
        MQEnvironment.port = cfg.getMqPort();
        try {
            queueManager = new MQQueueManager(cfg.getMqQueueManager());
            outQueue = queueManager.accessQueue(cfg.getMqQueue(), MQC.MQOO_OUTPUT);
        } catch (MQException e) {
            e.printStackTrace();
        }
    }

    public void put(final String messageContent)
    {
        try {
            putThrows(messageContent);
        } catch (Exception e) {
            throw new RuntimeException("Error putting message to ProvAdapter input queue.", e);
        }
    }

    void putThrows(final String messageContent) throws IOException, MQException
    {
        MQMessage message = createMsgWithDefaults();
        message.writeString(messageContent);
        outQueue.put(message);
    }

    private MQMessage createMsgWithDefaults() throws IOException
    {
        MQMessage message = new MQMessage();
        // Define a simple MQ message, and write some text in UTF format..
        message.format = MQC.MQFMT_STRING;
        message.feedback = MQC.MQFB_NONE;
        message.messageType = MQC.MQMT_DATAGRAM;
        message.replyToQueueManagerName = cfg.getMqQueueManager();
        MQPutMessageOptions pmo = new MQPutMessageOptions(); // accept the defaults, same
        // as MQPMO_DEFAULT constant
        message.clearMessage();
        message.messageId = MQC.MQMI_NONE;
        message.correlationId = MQC.MQCI_NONE;

        return message;
    }

}
