/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package scatours.blog.feed;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.abdera.Abdera;
import org.apache.abdera.factory.Factory;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.model.Person;
import org.apache.tuscany.sca.binding.atom.collection.NotFoundException;

public class BlogFeedImpl implements org.apache.tuscany.sca.binding.atom.collection.Collection {

    /**
     * Title of the blog.feed.
     */
    private static final String FEED_TITLE = "Tuscany SCA Tours Blog Feed";

    /**
     * Description of the blog feed.
     */
    private static final String FEED_DESCRIPTION = "Feed contianing the latest blog posts from Tuscany SCA Tours";

    /**
     * Author of the blog feed.
     */
    private static final String FEED_AUTHOR = "SCA Tours CEO";

    /**
     * Used to generate unique IDs for the blog entries.
     */
    private static final AtomicInteger ID_GEN = new AtomicInteger();

    /**
     * Gets a feed containing all the blog posts.
     * 
     * @return A feed containing all the blog posts.
     */
    public Feed getFeed() {
        final Factory factory = Abdera.getNewFactory();

        // Create SCA Tours blog feed
        final Feed feed = factory.newFeed();
        feed.setTitle(FEED_TITLE);
        feed.setSubtitle(FEED_DESCRIPTION);

        // Create blog author
        final Person author = factory.newAuthor();
        author.setName(FEED_AUTHOR);
        feed.addAuthor(author);

        // Create a sample entry
        final Entry entry = factory.newEntry();
        entry.setId(Integer.toString(ID_GEN.incrementAndGet()));
        entry.addAuthor(FEED_AUTHOR);
        entry.setTitle("Apache Tuscay in Action book features SCA Tours");
        entry.setContentAsHtml("We are famous as SCA Tours has been featured in the Apache Tuscany in Action book published by Manning");
        entry.setUpdated(new Date());
        entry.addLink("http://www.manning.com/laws/");
        feed.addEntry(entry);

        return feed;
    }

    /**
     * Query the feed.
     * 
     * @param query The query
     * @return Always returns null as method not implemented
     */
    public Feed query(String query) {
        // Not implemented
        return null;
    }

    /**
     * Posts a new entry to the blog.
     * 
     * @param entry The new entry
     * @return Always returns null as method not implemented
     */
    public Entry post(Entry entry) {
        // Not implemented
        return null;
    }

    /**
     * Gets the specified entry from the blog.
     * 
     * @param id ID of the entry to get
     * @return Not used
     * @throws NotFoundException Always thrown as method not implemented
     */
    public Entry get(String id) throws NotFoundException {
        // Not implemented
        throw new NotFoundException("You are not allowed to update entries");
    }

    /**
     * Updates the specified entry on the blog.
     * 
     * @param id ID of the entry to update
     * @param entry The new entry
     * @throws NotFoundException Always thrown as method not implemented
     */
    public void put(String id, Entry entry) throws NotFoundException {
        // Not implemented
        throw new NotFoundException("You are not allowed to update entries");
    }

    /**
     * Deletes the specified entry from the blog.
     * 
     * @param id ID of the entry to delete
     * @throws NotFoundException Always thrown as method not implemented
     */
    public void delete(String id) throws NotFoundException {
        // Not implemented
        throw new NotFoundException("You are not allowed to delete entries");
    }
}
