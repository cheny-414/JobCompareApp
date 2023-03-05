package edu.gatech.seclass.jobcompare6300;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Assert;

import androidx.annotation.IdRes;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.util.HumanReadables;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.seclass.jobcompare6300.db.JobEntity;

import org.slf4j.LoggerFactory;

/**
 * Created by dannyroa on 5/9/15.
 */
public class TestUtils {

	private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

	public static <VH extends RecyclerView.ViewHolder> ViewAction actionOnItemViewAtPosition(int position,
			@IdRes
					int viewId,
			ViewAction viewAction) {
		return new ActionOnItemViewAtPositionViewAction(position, viewId, viewAction);
	}


	private static final class ActionOnItemViewAtPositionViewAction<VH extends RecyclerView
			.ViewHolder>
			implements

			ViewAction {
		private final int position;
		private final ViewAction viewAction;
		private final int viewId;


		private ActionOnItemViewAtPositionViewAction(int position,
				@IdRes int viewId,
				ViewAction viewAction) {
			this.position = position;
			this.viewAction = viewAction;
			this.viewId = viewId;
		}


		public Matcher<View> getConstraints() {
			return Matchers.allOf(new Matcher[] {
					ViewMatchers.isAssignableFrom(RecyclerView.class), ViewMatchers.isDisplayed()
			});
		}


		public String getDescription() {
			return "actionOnItemAtPosition performing ViewAction: "
					+ this.viewAction.getDescription()
					+ " on item at position: "
					+ this.position;
		}


		public void perform(UiController uiController, View view) {
			RecyclerView recyclerView = (RecyclerView) view;
			(new ScrollToPositionViewAction(this.position)).perform(uiController, view);
			uiController.loopMainThreadUntilIdle();

			View targetView = recyclerView.getChildAt(this.position).findViewById(this.viewId);

			if (targetView == null) {
				throw (new PerformException.Builder()).withActionDescription(this.toString())
						.withViewDescription(
								HumanReadables.describe(view))
						.withCause(new IllegalStateException(
								"No view with id "
										+ this.viewId
										+ " found at position: "
										+ this.position))
						.build();
			}
			else {
				this.viewAction.perform(uiController, targetView);
			}
		}
	}

	private static final class ScrollToPositionViewAction implements ViewAction {
		private final int position;


		private ScrollToPositionViewAction(int position) {
			this.position = position;
		}


		public Matcher<View> getConstraints() {
			return Matchers.allOf(new Matcher[] {
					ViewMatchers.isAssignableFrom(RecyclerView.class), ViewMatchers.isDisplayed()
			});
		}


		public String getDescription() {
			return "scroll RecyclerView to position: " + this.position;
		}


		public void perform(UiController uiController, View view) {
			RecyclerView recyclerView = (RecyclerView) view;
			recyclerView.scrollToPosition(this.position);
		}
	}


	public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
		return new RecyclerViewMatcher(recyclerViewId);
	}

	public static Matcher<View> matchesBackgroundColor(final int expectedResourceId) {
		return new BoundedMatcher<View, View>(View.class) {
			int actualColor;
			int expectedColor;
			String message;

			@Override
			protected boolean matchesSafely(View item) {
				if (item.getBackground() == null) {
					message = item.getId() + " does not have a background";
					return false;
				}
				Resources resources = item.getContext().getResources();
				expectedColor = ResourcesCompat.getColor(resources, expectedResourceId, null);

	}

				actualColor = ((ColorDrawable) item.getBackground()).getColor();

				return actualColor == expectedColor;
			}
			@Override
			public void describeTo(final Description description) {
				if (actualColor != 0) { message = "Background color did not match: Expected "
						+  String.format("#%06X", (0xFFFFFF & expectedColor))
						+ " was " + String.format("#%06X", (0xFFFFFF & actualColor));
				}
				description.appendText(message);
			}
		};
	}
}

