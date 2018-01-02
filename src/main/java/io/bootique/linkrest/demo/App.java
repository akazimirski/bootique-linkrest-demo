package io.bootique.linkrest.demo;

import org.apache.cayenne.configuration.server.ServerModule;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.nhl.link.rest.runtime.cayenne.access.types.OffsetDateTimeType;

import io.bootique.Bootique;
import io.bootique.cayenne.CayenneModule;
import io.bootique.jersey.JerseyModule;
import io.bootique.linkrest.demo.api.DomainResource;

/**
 * A runnable Bootique + LinkRest + Cayenne application.
 */
public class App implements Module {

    public static void main(String[] args) throws Exception {
        Bootique.app(args).module(App.class).autoLoadModules().exec().exit();
    }

    @Override
    public void configure(Binder binder) {

        // add all classes in DomainResource's class package as REST API resources
        JerseyModule.extend(binder).addPackage(DomainResource.class.getPackage());
        
        // extending Cayenne with new supported type
        CayenneModule.extend(binder)
        .addModule(cayenneBinder -> {
        	ServerModule.contributeUserTypes(cayenneBinder).add(new OffsetDateTimeType()); 
        });
    }
}