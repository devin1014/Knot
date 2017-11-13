package com.android.smartlink.ui.model;

import com.android.devin.core.ui.widget.recyclerview.DiffContentAnnotation;
import com.android.devin.core.ui.widget.recyclerview.DiffItemAnnotation;
import com.android.devin.core.ui.widget.recyclerview.IDiffCompare;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-11-13
 * Time: 15:40
 */
public abstract class UICompareObject<T extends UICompareObject> implements IDiffCompare<T>
{
    @Override
    public boolean compareObject(UICompareObject newObj)
    {
        Method[] methods = getClass().getDeclaredMethods();

        try
        {
            for (Method method : methods)
            {
                if (!Modifier.isStatic(method.getModifiers()) && Modifier.isPublic(method.getModifiers()))
                {
                    DiffItemAnnotation annotation = method.getAnnotation(DiffItemAnnotation.class);

                    if (annotation != null)
                    {
                        Object oldResult = method.invoke(this);

                        Object newResult = method.invoke(newObj);

                        if (!oldResult.equals(newResult))
                        {
                            return false;
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

            return false;
        }

        return true;
    }

    private Map<String, Object> mBundle = new HashMap<>();

    @Override
    public boolean compareContent(UICompareObject newObj)
    {
        boolean compareContent = true;

        Method[] methods = getClass().getDeclaredMethods();

        try
        {
            for (Method method : methods)
            {
                if (!Modifier.isStatic(method.getModifiers()) && Modifier.isPublic(method.getModifiers()))
                {
                    DiffContentAnnotation annotation = method.getAnnotation(DiffContentAnnotation.class);

                    if (annotation != null)
                    {
                        Object oldResult = method.invoke(this);

                        Object newResult = method.invoke(newObj);

                        boolean compare = oldResult.equals(newResult);

                        if (!compare)
                        {
                            mBundle.put(method.getName(), newResult);
                        }

                        compareContent &= compare;
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

            compareContent = false;
        }

        return compareContent;
    }

    @Override
    public Object getChangePayload()
    {
        return mBundle;
    }
}
