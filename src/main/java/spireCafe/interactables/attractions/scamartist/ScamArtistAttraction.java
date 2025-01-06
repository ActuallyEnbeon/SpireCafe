package spireCafe.interactables.attractions.scamartist;

import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import spireCafe.Anniv7Mod;
import spireCafe.abstracts.AbstractAttraction;

public class ScamArtistAttraction extends AbstractAttraction {
    public static final String ID = ScamArtistAttraction.class.getSimpleName();
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack
            .getCharacterString(Anniv7Mod.makeID(ID));

    public ScamArtistAttraction(float animationX, float animationY) {
        super(animationX, animationY, 200, 200); // TODO change these
        this.animation = new SpriterAnimation(
                Anniv7Mod.makeAttractionPath("discord_statue/DiscordStatueIdle.scml"));
        authors = "Maddii & Enbeon";
        name = characterStrings.NAMES[0];
    }

    @Override
    public void renderCutscenePortrait(SpriteBatch sb) {

    }

    @Override
    public void onInteract() {
        if (!alreadyPerformedTransaction) {
            AbstractDungeon.topLevelEffectsQueue.add(new ScamArtistCutscene(this));
        }
    }
}
