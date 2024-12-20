package spireCafe.interactables.merchants.snackmaster;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import spireCafe.Anniv7Mod;
import spireCafe.abstracts.AbstractMerchant;
import spireCafe.interactables.merchants.snackmaster.food.LouseBurger;
import spireCafe.interactables.merchants.snackmaster.food.MawFillet;
import spireCafe.util.TexLoader;
import spireCafe.vfx.TopLevelSpeechEffect;

public class SnackmasterMerchant extends AbstractMerchant {
    public static final String ID = SnackmasterMerchant.class.getSimpleName();
    public static final int ZINGER_CUTOFF = 6;
    public static final CharacterStrings snackmasterStrings = CardCrawlGame.languagePack.getCharacterString(Anniv7Mod.makeID(ID));

    public SnackmasterMerchant(float animationX, float animationY) {
        super(animationX, animationY, 160.0f, 200.0f);
        this.name = snackmasterStrings.NAMES[0];
        this.authors = "Gk";
        this.img = TexLoader.getTexture(Anniv7Mod.makeMerchantPath("snackmaster/chef.png"));
        background = new TextureRegion(TexLoader.getTexture(Anniv7Mod.makeMerchantPath("snackmaster/shopscreen.png")));
    }

    @Override
    public void onInteract() {
        super.onInteract();
        if(AbstractDungeon.topLevelEffects.stream().noneMatch(e -> e instanceof TopLevelSpeechEffect)) {
            AbstractDungeon.topLevelEffects.add(new TopLevelSpeechEffect(Settings.WIDTH * 0.85f, (float) Settings.HEIGHT / 2, snackmasterStrings.TEXT[MathUtils.random(ZINGER_CUTOFF)], false));
        }
    }

    @Override
    public void rollShop() {
        articles.add(new LouseBurger(this, 0));
        articles.add(new MawFillet(this, 1));
        articles.add(new LouseBurger(this, 2));
    }
}
