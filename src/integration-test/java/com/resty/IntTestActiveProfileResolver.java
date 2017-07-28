package com.resty;

import org.springframework.test.context.ActiveProfilesResolver;

/**
 * Resolver to set the active profile to integrationTest if it's not already set.
 *
 * Created by damianhagge on 5/26/17.
 */
public class IntTestActiveProfileResolver implements ActiveProfilesResolver {

    @Override
    public String[] resolve(final Class<?> someClass) {
        final String activeProfileEnv = System.getenv("SPRING_PROFILES_ACTIVE");
        final String activeProfileProp = System.getProperty("spring.profiles.active");

        String profile = activeProfileEnv != null ? activeProfileEnv : null;
        if (profile == null && activeProfileProp != null) {
            profile = activeProfileProp;
        }
        return new String[] { profile == null ? "integrationTest" : profile };
    }
}
