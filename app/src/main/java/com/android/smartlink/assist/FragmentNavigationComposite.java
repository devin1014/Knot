package com.android.smartlink.assist;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;

import com.android.smartlink.R;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;

public class FragmentNavigationComposite
{
    public static final int FRAGMENT_MODE_PRIMARY = 0;

    public static final int FRAGMENT_MODE_SECONDARY = 1;

    public static final int FRAGMENT_MODE_OVERLAY = 2;

    public static final String BACK_STACK_PRIMARY = "com.neulion.template.ui.composite.FragmentNavigationComposite.back_stack.PRIMARY";

    public static final String BACK_STACK_SECONDARY = "com.neulion.template.ui.composite.FragmentNavigationComposite.back_stack.SECONDARY";

    private final Context mContext;

    private final FragmentManager mFragmentManager;

    private final FragmentNavigationCompositeCallback mFragmentNavigationCompositeCallback;

    private Fragment mCurrentFragment;

    @SuppressWarnings("UnusedDeclaration")
    public FragmentNavigationComposite(Context context, FragmentManager fragmentManager)
    {
        this(context, fragmentManager, null);
    }

    public FragmentNavigationComposite(Context context, FragmentManager fragmentManager, FragmentNavigationCompositeCallback callback)
    {
        // initialize fields.
        mContext = context;

        mFragmentManager = fragmentManager;

        mFragmentNavigationCompositeCallback = callback;

        // initialize fragment manager.
        fragmentManager.addOnBackStackChangedListener(mOnBackStackChangedListener);
    }

    // -------------------------------------------------------------------------------------------------------------------------------------
    // Interface (Callback)
    // -------------------------------------------------------------------------------------------------------------------------------------
    protected void onFragmentsChanged()
    {
        if (mFragmentManager.getBackStackEntryCount() == 0)
        {
            return;
        }

        mCurrentFragment = mFragmentManager.findFragmentById(R.id.fragment_page);

        if (mFragmentNavigationCompositeCallback != null)
        {
            mFragmentNavigationCompositeCallback.onFragmentsChanged();
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------------------
    // Interfaces (Lifecycle)
    // -------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Call this method in {@link android.app.Activity#onPostCreate(Bundle)}.
     */
    @SuppressWarnings("UnusedDeclaration")
    public void restoreState(Bundle savedInstanceState) throws NullPointerException
    {
        if (savedInstanceState == null)
        {
            throw new NullPointerException("Saved instance state cannot be null.");
        }

        onFragmentsChanged();
    }

    /**
     * Call this method in {@link android.app.Activity#onDestroy()}.
     */
    @SuppressWarnings("UnusedDeclaration")
    public void destroy()
    {
        mCurrentFragment = null;

        mFragmentManager.removeOnBackStackChangedListener(mOnBackStackChangedListener);
    }

    // -------------------------------------------------------------------------------------------------------------------------------------
    // Interfaces (Event)
    // -------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Call this method when the home button pressed.
     */
    @SuppressWarnings("UnusedDeclaration")
    public boolean onHomePressed()
    {
        return getCurrentFragmentMode() == FRAGMENT_MODE_SECONDARY && mFragmentManager.popBackStackImmediate();
    }

    /**
     * Call this method when the back button pressed.
     */
    @SuppressWarnings("UnusedDeclaration")
    public boolean onBackPressed()
    {
        // back for current page.
        BaseSmartlinkFragment fragment = (BaseSmartlinkFragment) mFragmentManager.findFragmentById(R.id.fragment_page);

        if (fragment != null && fragment.onBackPressed())
        {
            return true;
        }

        // pop the back stack.

        switch (getCurrentFragmentMode())
        {
            case FRAGMENT_MODE_SECONDARY:

                mFragmentManager.popBackStackImmediate();

                return true;

            case FRAGMENT_MODE_OVERLAY:

                // TODO support overlay fragment.

                return true;
        }

        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------------------
    // Interfaces (Fragment)
    // -------------------------------------------------------------------------------------------------------------------------------------

    @SuppressWarnings("UnusedDeclaration")
    public void showPrimaryFragment(Fragment fragment, int title) throws IllegalStateException
    {
        showPrimaryFragmentInternal(fragment, title, null);
    }

    @SuppressWarnings("UnusedDeclaration")
    public void showPrimaryFragment(Fragment fragment, CharSequence title) throws IllegalStateException
    {
        showPrimaryFragmentInternal(fragment, 0, title);
    }

    @SuppressWarnings("UnusedDeclaration")
    public void showSecondaryFragment(Fragment fragment, int title) throws IllegalStateException
    {
        showSecondaryFragmentInternal(fragment, title, null);
    }

    @SuppressWarnings("UnusedDeclaration")
    public void showSecondaryFragment(Fragment fragment, CharSequence title) throws IllegalStateException
    {
        showSecondaryFragmentInternal(fragment, 0, title);
    }

    @SuppressWarnings("UnusedDeclaration")
    public boolean finishFragment(Fragment fragment)
    {
        if (fragment == mCurrentFragment)
        {
            mFragmentManager.popBackStack();

            return true;
        }

        return false;
    }

    public CharSequence getTitle()
    {
        CharSequence title = null;

        final int count = mFragmentManager.getBackStackEntryCount();

        for (int i = count - 1; i >= 0; i--)
        {
            final BackStackEntry backStackEntry = mFragmentManager.getBackStackEntryAt(i);

            if (backStackEntry != null)
            {
                title = backStackEntry.getBreadCrumbTitle();

                if (title != null)
                {
                    break;
                }
            }
        }

        return title;
    }

    // -------------------------------------------------------------------------------------------------------------------------------------
    // Interfaces (Instantiate Fragment)
    // -------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Instantiate {@link android.support.v4.app.Fragment} by ClassName.
     */
    public Fragment instantiateFragment(String fragmentClassName, Bundle arguments)
    {
        Fragment fragment = null;

        try
        {
            fragment = Fragment.instantiate(mContext, fragmentClassName, arguments);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }

        return fragment;
    }

    // -------------------------------------------------------------------------------------------------------------------------------------
    // Tools
    // -------------------------------------------------------------------------------------------------------------------------------------

    public boolean isPrimaryMode()
    {
        return getCurrentFragmentMode() == FRAGMENT_MODE_PRIMARY;
    }

    public int getCurrentFragmentMode()
    {
        if (mFragmentManager.getBackStackEntryCount() > 1)
        {
            // TODO support overlay fragment.

            return FRAGMENT_MODE_SECONDARY;
        }
        else
        {
            return FRAGMENT_MODE_PRIMARY;
        }
    }

    public Fragment getCurrentFragment()
    {
        return mCurrentFragment;
    }

    private void showPrimaryFragmentInternal(Fragment fragment, int titleResource, CharSequence titleString) throws IllegalStateException
    {
        if (fragment != null)
        {
            // clear all fragments and create a new one.

            mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            // show fragment.

            final FragmentTransaction transaction = mFragmentManager.beginTransaction();

            transaction.replace(R.id.fragment_page, fragment);

            transaction.addToBackStack(BACK_STACK_PRIMARY);

            if (titleResource != 0)
            {
                transaction.setBreadCrumbTitle(titleResource);
            }
            else if (titleString != null)
            {
                transaction.setBreadCrumbTitle(titleString);
            }

            // transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

            transaction.commit();
        }
        else
        {
            // just clear the other fragments.

            mFragmentManager.popBackStackImmediate(BACK_STACK_SECONDARY, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    private void showSecondaryFragmentInternal(Fragment fragment, int titleResource, CharSequence titleString) throws IllegalStateException
    {
        final FragmentTransaction transaction = mFragmentManager.beginTransaction();

        // hide current fragment.
        if (mFragmentManager.getBackStackEntryCount() > 2)
        {
            mFragmentManager.popBackStackImmediate(BACK_STACK_SECONDARY, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        final Fragment current = mCurrentFragment;

        if (current != null)
        {
            transaction.hide(current);
        }

        // show fragment.

        transaction.add(R.id.fragment_page, fragment);

        transaction.addToBackStack(BACK_STACK_SECONDARY);

        if (titleResource != 0)
        {
            transaction.setBreadCrumbTitle(titleResource);
        }
        else if (titleString != null)
        {
            transaction.setBreadCrumbTitle(titleString);
        }

        //         transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        transaction.commit();
    }

    private final OnBackStackChangedListener mOnBackStackChangedListener = new OnBackStackChangedListener()
    {
        @Override
        public void onBackStackChanged()
        {
            onFragmentsChanged();
        }
    };

    // -------------------------------------------------------------------------------------------------------------------------------------
    // Callback
    // -------------------------------------------------------------------------------------------------------------------------------------

    public interface FragmentNavigationCompositeCallback
    {
        void onFragmentsChanged();
    }
}