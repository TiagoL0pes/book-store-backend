package com.system.store.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.system.store.models.Author;
import com.system.store.models.Book;
import org.bson.types.ObjectId;

public class BookTemplate implements TemplateLoader {

    public static final String MOCK = "mock";

    @Override
    public void load() {
        ObjectId id = new ObjectId();

        Fixture.of(Book.class).addTemplate(MOCK, new Rule() {{
            add("id", id);
            add("isbn", "9788581633671");
            add("title", "The notebook");
            add("author", one(Author.class, AuthorTemplate.MOCK));
        }});
    }
}
