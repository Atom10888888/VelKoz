package velKoz.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import velKoz.VelKoz;
import velKoz.powers.DeconstructionPower;
import velKoz.powers.VoidInsightPower;
import velKoz.util.TextureLoader;

import static velKoz.VelKoz.makeRelicOutlinePath;
import static velKoz.VelKoz.makeRelicPath;

public class PlaceholderRelic2 extends CustomRelic {
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * At the start of each combat, gain 1 Strength (i.e. Vajra)
     */

    // ID, images, text.
    public static final String ID = VelKoz.makeID("PlaceholderRelic2");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic2.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic2.png"));

    public PlaceholderRelic2() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }


    // Gain 1 Strength on on equip.
    @Override
    public void atBattleStart() {
        flash();
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(
                        AbstractDungeon.player, // 应用能力的对象，这里是玩家
                        AbstractDungeon.player, // 能力的来源，也是玩家
                        new VoidInsightPower(AbstractDungeon.player, AbstractDungeon.player, 15, 1, 3), // 创建一个新的能力实例
                        15 // 要添加的能力层数
                )
        );
        for(AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, AbstractDungeon.player, new DeconstructionPower(mo, AbstractDungeon.player, 0),0));
        }
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }


    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
