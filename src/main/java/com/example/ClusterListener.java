package com.example;

import akka.actor.AbstractActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import static akka.cluster.ClusterEvent.initialStateAsEvents;

public class ClusterListener extends AbstractActor {

  final LoggingAdapter log = Logging.getLogger(this);
  final Cluster cluster = Cluster.get(context().system());

  @Override
  public void preStart() throws Exception {
    log.debug("starting up cluster listener...");
    cluster.subscribe(self(), initialStateAsEvents(), ClusterEvent.ClusterDomainEvent.class);
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder()
        .match(ClusterEvent.CurrentClusterState.class, state -> {
          log.debug("Current members: {}", state.members());
        })
        .match(ClusterEvent.MemberUp.class, event -> {
          log.debug("Member is Up: {}", event.member().address());
        })
        .match(ClusterEvent.UnreachableMember.class, event -> {
          log.debug("Member detected as unreachable: {}", event.member());

        })
        .match(ClusterEvent.MemberRemoved.class, event -> {
          log.debug("Member is Removed: {} after {}", event.member().address(), event.previousStatus());
        })
        .match(ClusterEvent.MemberEvent.class, event -> {
          log.info("Member Event: " + event.toString());
        })
        .build();
  }
}
