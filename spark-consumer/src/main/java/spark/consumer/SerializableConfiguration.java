package spark.consumer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

public class SerializableConfiguration implements java.io.Serializable{
    private transient Configuration configuration;
    public SerializableConfiguration(Configuration configuration){
        this.configuration = configuration;
    }
    public Configuration getConfiguration(){
        if(configuration == null)
            configuration = HBaseConfiguration.create();
        return configuration;
    }
}
