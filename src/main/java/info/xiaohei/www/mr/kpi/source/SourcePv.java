package info.xiaohei.www.mr.kpi.source;

import info.xiaohei.www.mr.BaseDriver;
import info.xiaohei.www.mr.JobInitModel;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;

/**
 * Created by xiaohei on 16/2/21.
 * <p/>
 * 用户访问来源统计
 */
public class SourcePv {
    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
        String[] inPath = new String[]{"hdfs://localhost:9000/data/1-kpi/*"};
        String outPath = "hdfs://localhost:9000/out/1-kpi/source";
        Configuration conf = new Configuration();
        String jobName = "source-pv";

        JobInitModel job = new JobInitModel(inPath, outPath, conf, null, jobName
                , SourcePv.class, Mapper.class, Text.class, IntWritable.class, Reducer.class
                , Text.class, IntWritable.class);

        JobInitModel sortJob = new JobInitModel(new String[]{outPath + "/part-*"}, outPath + "/sort", conf, null
                , jobName + "sort", SourcePv.class, Mapper.class, Text.class, IntWritable.class, null, null, null);

        BaseDriver.initJob(new JobInitModel[]{job, sortJob});
    }
}
