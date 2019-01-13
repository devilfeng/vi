package com.ctrip.framework.cs.enterprise;

import com.ctrip.framework.cs.component.Refreshable;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by jiang.j on 2016/10/19.
 */
public interface EnApp extends Refreshable {
    String getVersion();

    String getName();

    String getChineseName();

    String getOwner();

    String getOwnerContact();

    String getBackup();

    String getBuildTime();

    List<ServerInfo> getAllServers();

    Map<String, String> getHelpLinks();

    Properties getProperties();

    void register();

    boolean trace(String traceId);

    String getGitCommitId();

    String getGitPrjPath();
}
