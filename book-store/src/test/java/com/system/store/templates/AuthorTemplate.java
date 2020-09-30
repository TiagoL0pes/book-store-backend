package com.system.store.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.system.store.models.Author;
import org.bson.types.ObjectId;

public class AuthorTemplate implements TemplateLoader {

    public static final String MOCK = "mock";

    @Override
    public void load() {
        ObjectId id = new ObjectId();

        Fixture.of(Author.class).addTemplate(MOCK, new Rule() {{
            add("id", id);
            add("name", "Nicholas Sparks");
            add("email", "nicholas.sparks@email.com");
        }});
    }
}
