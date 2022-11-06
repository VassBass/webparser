package com.vassbassapp.scrapper;

import java.util.Collection;
import java.util.concurrent.Callable;

public interface Scrapper<S> extends Callable<Collection<S>> {
}
