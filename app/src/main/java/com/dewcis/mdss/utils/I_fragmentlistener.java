package com.dewcis.mdss.utils;

/**
 * Created by Arwin KIsh on 7/11/2017.
 */
public interface I_fragmentlistener<T,Y, P, D> {
    public void onData(T t);
    public void ansQuestion(Y y);
    public void ansQuestionPregnant(P p);
    public void isDraft(boolean t);

    boolean getBol();
}
