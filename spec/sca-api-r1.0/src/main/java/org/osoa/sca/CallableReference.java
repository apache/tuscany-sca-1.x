package org.osoa.sca;

/**
 * Common superclass for references that can be passed between components.
 * 
 * @version $Rev$ $Date$
 * @param <B> the Java interface associated with this reference
 */
public interface CallableReference<B> {
    /**
     * Returns a type-safe reference to the target of this reference.
     * The instance returned is guaranteed to implement the business interface for this reference
     * but may not be a proxy as defined by java.lang.reflect.Proxy.
     *
     * @return a proxy to the target that implements the business interface associated with this reference
     */
    B getService();

    /**
     * Returns the Java class for the business interface associated with this reference.
     *
     * @return the Class for the business interface associated with this reference
     */
    Class<B> getBusinessInterface();

    /**
     * Returns true if this reference is conversational.
     *
     * @return true if this reference is conversational
     */
    boolean isConversational();

    /**
     * Returns the conversation associated with this reference.
     * Returns null if no conversation is currently active.
     *
     * @return the conversation associated with this reference; may be null
     */
    Conversation getConversation();

    /**
     * Returns the callback ID.
     *
     * @return the callback ID
     */
    Object getCallbackID();
}
