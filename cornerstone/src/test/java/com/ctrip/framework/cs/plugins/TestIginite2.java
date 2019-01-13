package com.ctrip.framework.cs.plugins;

import com.ctrip.framework.cs.IgniteManager;
import com.ctrip.framework.cs.annotation.Ignite;
import com.ctrip.framework.cs.ignite.IgnitePlugin;

/**
 * Created by jiang.j on 2016/8/22.
 */
@Ignite(id = "vi.ignite2", after = {"vi.ignite1", "vi.ignite4"})
public class TestIginite2 implements IgnitePlugin {
    @Override
    public boolean run(IgniteManager.SimpleLogger logger) {
        return true;
    }
}
