package me.nonamegmm.mcscore.utils.economy;

import com.minecraft.economy.apis.UltiEconomyAPI;
import me.nonamegmm.mcscore.MCSCore;

public class Competitive {
    public String[] Competitors = new String[10];

    UltiEconomyAPI economy = MCSCore.getEconomy();
    public void Init()
    {
        for (int i = 0; i < 10; i++) {
            economy.addTo(Competitors[i], 800.00);
        }
    }
}
