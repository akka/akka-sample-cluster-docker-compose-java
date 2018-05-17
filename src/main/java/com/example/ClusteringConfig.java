package com.example;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ClusteringConfig {
  private static Config config = ConfigFactory.load();

  public static final String CLUSTER_NAME = config.getString("clustering.cluster.name");

}
