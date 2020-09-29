package com.system.store.templates;

import com.system.store.models.User;
import org.bson.types.ObjectId;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class UserTemplate implements TemplateLoader {

    public static final String MOCK = "mock";

    @Override
    public void load() {
    	ObjectId id = new ObjectId();
        Fixture.of(User.class).addTemplate(MOCK, new Rule() {{
            add("id", id);
            add("email", "email@email.com");
            add("password", "1234");
        }});

    }

}
