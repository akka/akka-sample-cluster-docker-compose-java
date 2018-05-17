package com.example;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class ClusteringApp {

  public static void main(String[] args) {
    ActorSystem system = ActorSystem.create(ClusteringConfig.CLUSTER_NAME);

    ActorRef clusterListener = system.actorOf(Props.create(ClusterListener.class), "clusterListener");
  }

}
