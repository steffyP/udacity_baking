package nanodegree.udacity.stefanie.at.bakingmaster;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import nanodegree.udacity.stefanie.at.bakingmaster.database.data.Recipe;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.BundleMatchers.hasEntry;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtras;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static nanodegree.udacity.stefanie.at.bakingmaster.InstructionActivity.EXTRA_RECIPE;
import static nanodegree.udacity.stefanie.at.bakingmaster.StepDetailsActivity.EXTRA_POSITION;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assume.assumeTrue;

/**
 * Created by steffy on 03/09/2017.
 */

@RunWith(AndroidJUnit4.class)
public class InstructionStepSelectionTest {

    private String recipeJsonString = "{\n" +
            "    \"id\": 1,\n" +
            "    \"name\": \"Test Recipe\",\n" +
            "    \"ingredients\": [\n" +
            "      {\n" +
            "        \"quantity\": 2,\n" +
            "        \"measure\": \"CUP\",\n" +
            "        \"ingredient\": \"Graham Cracker crumbs\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 6,\n" +
            "        \"measure\": \"TBLSP\",\n" +
            "        \"ingredient\": \"unsalted butter, melted\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 0.5,\n" +
            "        \"measure\": \"CUP\",\n" +
            "        \"ingredient\": \"granulated sugar\"\n" +
            "      }" +
            "    ],\n" +
            "    \"steps\": [\n" +
            "      {\n" +
            "        \"id\": 0,\n" +
            "        \"shortDescription\": \"Recipe Step 1\",\n" +
            "        \"description\": \"Recipe Introduction of step 1\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 1,\n" +
            "        \"shortDescription\": \"Starting prep\",\n" +
            "        \"description\": \"2. this is the description for task 2\",\n" +
            "        \"videoURL\": \"\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 2,\n" +
            "        \"shortDescription\": \"Step 3\",\n" +
            "        \"description\": \"This is a longer description for step 3. It should be longer just to for testing reasons. It could have been shorter though. This is just a dummy text.\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9a6_2-mix-sugar-crackers-creampie/2-mix-sugar-crackers-creampie.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      }" +
            "    ],\n" +
            "    \"servings\": 4,\n" +
            "    \"image\": \"\"\n" +
            "  }";
    @Rule
    public ActivityTestRule<InstructionActivity> mActivityRule = new ActivityTestRule<>(
            InstructionActivity.class, true, false);
    private Recipe recipe;

    @Before
    public void setup() {
        Context targetContext = InstrumentationRegistry.getInstrumentation()
                .getTargetContext();
        Intent intent = new Intent(targetContext, InstructionActivity.class);
        recipe = null;
        try {
            recipe = new Recipe(new JSONObject(recipeJsonString));
            intent.putExtra(EXTRA_RECIPE, recipe);

        } catch (JSONException e) {
            Assert.fail("problem while doing the setup, the JSON recipe is not valid!");
        }

        mActivityRule.launchActivity(intent);
    }

    @Test
    public void testStepsShown() {


        onView(withId(R.id.recycler_view_steps)).check(matches(hasDescendant(withText("Recipe Step 1"))));
        onView(withId(R.id.recycler_view_steps)).check(new RecylcerViewItemCountAssertion(4)); // 3 steps + 1 for instructions

    }


    @Test
    public void testSelectFirstStepPhone() {
        assumeTrue(!isTwoPane());

        Intents.init();

        onView(withId(R.id.recycler_view_steps))
                .perform(actionOnItemAtPosition(0,
                        click()));
        intended(allOf(hasExtras(allOf(hasEntry(EXTRA_RECIPE, recipe), hasEntry(EXTRA_POSITION, 0))), hasComponent(StepDetailsActivity.class.getName())));

        Intents.release();

    }

    @Test
    public void testSelectSecondTablet() {
        // the two pane mode will only shown on tablet in Landscape mode!
        assumeTrue(isTwoPane());


        onView(withId(R.id.recycler_view_steps))
                .perform(actionOnItemAtPosition(2,
                        click()));

        onView(withId(R.id.details)).check(matches(withText("2. this is the description for task 2")));
        onView(withId(R.id.previous)).check(matches(isDisplayed()));
        onView(withId(R.id.next)).check(matches(isDisplayed()));

    }


    public boolean isTwoPane() {
        return mActivityRule.getActivity().getResources().getInteger(R.integer.sw_600) == 0 ? false : true;
    }
}
