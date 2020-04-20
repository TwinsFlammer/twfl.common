package com.redefocus.common.shared.server.util;

import java.util.Map;

/**
 * Created by zh32 on 16.04.16.
 */
public interface StatusResponse {
	
    int getPlayerOnline();

    int getPlayerMax();

    Map<String, String> getPlayerSample();

    String getIcon();

    String getProtocolName();

    int getProtocolVersion();
}
