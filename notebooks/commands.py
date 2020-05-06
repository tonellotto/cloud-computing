'''
This file contains the definition of the commands and configuration files used in the notebook.
DO NOT MODIFY IT FOR ANY REASON.
IF YOU DO, YOU CAN COMPROMISE YOUR VIRTUAL MACHINES
'''

WGET_CMD = 'wget --progress=bar:force -c -O /home/hadoop/hadoop.tar.gz https://archive.apache.org/dist/hadoop/common/hadoop-3.1.3/hadoop-3.1.3.tar.gz'
TAR_CMD = 'tar -xvf hadoop.tar.gz --directory=/opt/hadoop --exclude=hadoop-3.1.0/share/doc --strip 1 > /dev/null'
RM_CMD = 'rm /home/hadoop/hadoop.tar.gz'


def get_bashrc():
    bashrc_string = '''export HADOOP_HOME=/opt/hadoop
export PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:\\$HADOOP_HOME/bin:\\$HADOOP_HOME/sbin
export LD_LIBRARY_PATH=\\$HADOOP_HOME/lib/native:\\$LD_LIBRARY_PATH
export HADOOP_CONF_DIR=\\$HADOOP_HOME/etc/hadoop
export HDFS_NAMENODE_USER=hadoop
export HDFS_DATANODE_USER=hadoop
export HDFS_SECONDARYNAMENODE_USER=hadoop
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
export HADOOP_MAPRED_HOME=\\$HADOOP_HOME
export HADOOP_COMMON_HOME=\\$HADOOP_HOME
export HADOOP_HDFS_HOME=\\$HADOOP_HOME
export YARN_HOME=\\$HADOOP_HOME
export HADOOP_LOG_DIR=\\$HADOOP_HOME/logs'''
    return '"' + bashrc_string.replace('\n', '" "') + '"'


def get_namenode_core_site(namenode_hostname):
    namenode_core_site = f'''
<configuration>
  <property>
    <name>fs.defaultFS</name>
    <value>hdfs://{namenode_hostname}:9820/</value>
  </property>
</configuration>
    '''
    return '"' + namenode_core_site.replace('\n', '" "') + '"'


def get_namenode_hdfs_site():
    namenode_hdfs_site = f'''
<configuration>
  <property>
    <name>dfs.namenode.name.dir</name>
    <value>file:///opt/hdfs/namenode</value>
  </property>
  <property>
    <name>dfs.datanode.data.dir</name>
    <value>file:///opt/hdfs/datanode</value>
  </property>
  <property>
    <name>dfs.replication</name>
    <value>2</value>
  </property>
  <property>
    <name>dfs.permissions</name>
    <value>false</value>
  </property>
  <property>
    <name>dfs.datanode.use.datanode.hostname</name>
    <value>false</value>
  </property>
</configuration>
    '''
    return '"' + namenode_hdfs_site.replace('\n', '" "') + '"'


def get_namenode_yarn_site():
    namenode_yarn_site = f'''
<configuration>
  <property>
    <name>yarn.nodemanager.aux-services</name>
    <value>mapreduce_shuffle</value>
  </property>
  <property>
    <name>yarn.resourcemanager.hostname</name>
    <value>hadoop-namenode</value>
  </property>
  <property>
    <name>yarn.nodemanager.aux-services.mapreduce.shuffle.class</name>
    <value>org.apache.hadoop.mapred.ShuffleHandler</value>
  </property>
  <property>
    <name>yarn.nodemanager.local-dirs</name>
    <value>file:///opt/yarn/local</value>
  </property>
  <property>
    <name>yarn.nodemanager.log-dirs</name>
    <value>file:///opt/yarn/logs</value>
  </property>
  <property>
    <name>yarn.nodemanager.resource.memory-mb</name>
    <value>1536</value>
  </property>
  <property>
    <name>yarn.scheduler.maximum-allocation-mb</name>
    <value>1536</value>
  </property>
  <property>
    <name>yarn.scheduler.minimum-allocation-mb</name>
    <value>128</value>
  </property>
  <property>
    <name>yarn.nodemanager.vmem-check-enabled</name>
    <value>false</value>
  </property>
</configuration>
    '''
    return '"' + namenode_yarn_site.replace('\n', '" "') + '"'


def get_namenode_mapred_site(namenode_hostname):
    namenode_mapred_site = f'''
<configuration>
  <property>
    <name>mapreduce.framework.name</name>
    <value>yarn</value>
  </property>
  <property>
    <name>mapreduce.jobhistory.address</name>
    <value>{namenode_hostname}:10020</value>
  </property>
  <property>
    <name>mapreduce.jobhistory.webapp.address</name>
    <value>{namenode_hostname}:19888</value>
  </property>
  <property>
    <name>mapreduce.jobhistory.intermediate-done-dir</name>
    <value>/mr-history/tmp</value>
  </property>
  <property>
    <name>mapreduce.jobhistory.done-dir</name>
    <value>/mr-history/done</value>
  </property>
  <property>
    <name>yarn.app.mapreduce.am.env</name>
    <value>HADOOP_MAPRED_HOME=/opt/hadoop</value>
  </property>
  <property>
    <name>mapreduce.map.env</name>
    <value>HADOOP_MAPRED_HOME=/opt/hadoop</value>
  </property>
  <property>
    <name>mapreduce.reduce.env</name>
    <value>HADOOP_MAPRED_HOME=/opt/hadoop</value>
  </property>
  <property>
    <name>yarn.app.mapreduce.am.resource.mb</name>
    <value>512</value>
  </property>
  <property>
    <name>mapreduce.map.memory.mb</name>
    <value>256</value>
  </property>
  <property>
    <name>mapreduce.reduce.memory.mb</name>
    <value>256</value>
  </property>
</configuration>
    '''
    return '"' + namenode_mapred_site.replace('\n', '" "') + '"'


def get_workers(vms):
    return '"' + '" "'.join(vms.keys()) + '"'


def get_datanode_core_site(namenode_hostname):
    datanode_core_site = f'''
<configuration>
  <property>
    <name>fs.defaultFS</name>
    <value>hdfs://{namenode_hostname}:9820/</value>
  </property>
</configuration>
'''
    return '"' + datanode_core_site.replace('\n', '" "') + '"'


def get_datanode_hdfs_site():
    datanode_hdfs_site = f'''
<configuration>
  <property>
    <name>dfs.datanode.data.dir</name>
    <value>file:///opt/hdfs/datanode</value>
  </property>
  <property>
    <name>dfs.replication</name>
    <value>2</value>
  </property>
  <property>
    <name>dfs.permissions</name>
    <value>false</value>
  </property>
  <property>
    <name>dfs.datanode.use.datanode.hostname</name>
    <value>false</value>
  </property>
</configuration>
'''
    return '"' + datanode_hdfs_site.replace('\n', '" "') + '"'


def get_datanode_yarn_site():
    datanode_yarn_site = f'''
<configuration>
  <property>
    <name>yarn.nodemanager.aux-services</name>
    <value>mapreduce_shuffle</value>
  </property>
  <property>
    <name>yarn.resourcemanager.hostname</name>
    <value>hadoop-namenode</value>
  </property>
  <property>
    <name>yarn.nodemanager.resource.memory-mb</name>
    <value>1536</value>
  </property>
  <property>
    <name>yarn.scheduler.maximum-allocation-mb</name>
    <value>1536</value>
  </property>
  <property>
    <name>yarn.scheduler.minimum-allocation-mb</name>
    <value>128</value>
  </property>
  <property>
    <name>yarn.nodemanager.vmem-check-enabled</name>
    <value>false</value>
  </property>
</configuration>
'''
    return '"' + datanode_yarn_site.replace('\n', '" "') + '"'


def get_datanode_mapred_site():
    datanode_mapred_site = f'''
<configuration>
  <property>
    <name>mapreduce.framework.name</name>
    <value>yarn</value>
  </property>
  <property>
    <name>yarn.app.mapreduce.am.env</name>
    <value>HADOOP_MAPRED_HOME=/opt/hadoop</value>
  </property>
  <property>
    <name>mapreduce.map.env</name>
    <value>HADOOP_MAPRED_HOME=/opt/hadoop</value>
  </property>
  <property>
    <name>mapreduce.reduce.env</name>
    <value>HADOOP_MAPRED_HOME=/opt/hadoop</value>
  </property>
  <property>
    <name>yarn.app.mapreduce.am.resource.mb</name>
    <value>512</value>
  </property>
  <property>
    <name>mapreduce.map.memory.mb</name>
    <value>256</value>
  </property>
  <property>
    <name>mapreduce.reduce.memory.mb</name>
    <value>256</value>
  </property>
</configuration>
'''
    return '"' + datanode_mapred_site.replace('\n', '" "') + '"'


def get_pom():
    pom = f'''<project xmlns=\\"http://maven.apache.org/POM/4.0.0\\" xmlns:xsi=\\"http://www.w3.org/2001/XMLSchema-instance\\"
  xsi:schemaLocation=\\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\\">
  <modelVersion>4.0.0</modelVersion>
  <groupId>it.unipi.hadoop</groupId>
  <artifactId>wordcount</artifactId>
  <packaging>jar</packaging>
  <version>1.0-SNAPSHOT</version>
  <name>wordcount</name>
  <url>http://maven.apache.org</url>
  
  <properties>
    <java.version>1.8</java.version>
    <hadoop.version>3.1.3</hadoop.version>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  </properties>

  <build>
    <plugins>
      <plugin>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.2</version>
        <configuration>
          <source>\\${{java.version}}</source>
          <target>\\${{java.version}}</target>
          <encoding>\\${{project.build.sourceEncoding}}</encoding>
        </configuration>
      </plugin>

      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-jar-plugin</artifactId>
        <version>3.2.0</version>
        <configuration>
          <archive>
            <manifest>
              <addClasspath>true</addClasspath>
            </manifest>
          </archive>
        </configuration>
      </plugin>
    </plugins>
  </build>

  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.apache.hadoop</groupId>
      <artifactId>hadoop-mapreduce-client-jobclient</artifactId>
      <version>\\${{hadoop.version}}</version>
    </dependency>
    <dependency>
      <groupId>org.apache.hadoop</groupId>
      <artifactId>hadoop-common</artifactId>
      <version>\\${{hadoop.version}}</version>
    </dependency>
    <dependency>
      <groupId>org.apache.hadoop</groupId>
      <artifactId>hadoop-hdfs-client</artifactId>
      <version>\\${{hadoop.version}}</version>
    </dependency>
    <dependency>
      <groupId>org.apache.hadoop</groupId>
      <artifactId>hadoop-mapreduce-client-app</artifactId>
      <version>\\${{hadoop.version}}</version>
    </dependency>
  </dependencies>
</project>'''
    return '"' + pom.replace('\n', '" "') + '"'
