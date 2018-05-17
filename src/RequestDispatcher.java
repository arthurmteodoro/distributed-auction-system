import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.blocks.MessageDispatcher;
import org.jgroups.blocks.RequestHandler;
import org.jgroups.blocks.RequestOptions;
import org.jgroups.blocks.ResponseMode;
import org.jgroups.util.RspList;

import java.util.Collection;

public class RequestDispatcher
{
    private MessageDispatcher dispatcher;

    public RequestDispatcher(JChannel channel, RequestHandler requestHandler)
    {
        if(channel != null)
        {
            this.dispatcher = new MessageDispatcher(channel, null, null, requestHandler);
        }
    }

    public RspList sendRequestMulticast(Object value, ResponseMode responseMode) throws Exception
    {
        Message message = new Message(null, value);

        RequestOptions options = new RequestOptions();
        options.setMode(responseMode);
        options.setAnycasting(false);

        return this.dispatcher.castMessage(null, message, options);
    }

    public RspList sendRequestMulticast(Object value, ResponseMode responseMode, Address removeAdd) throws Exception
    {
        Message message = new Message(null, value);

        RequestOptions options = new RequestOptions();
        options.setMode(responseMode);
        options.setAnycasting(false);
        options.setExclusionList(removeAdd);

        return this.dispatcher.castMessage(null, message, options);
    }

    public RspList sendRequestAnycast(Collection<Address> cluster, Object value, ResponseMode responseMode) throws Exception
    {
        Message message = new Message(null, value);

        RequestOptions options = new RequestOptions();
        options.setMode(responseMode);
        options.setAnycasting(true);

        return this.dispatcher.castMessage(cluster, message, options);
    }

    public Object sendRequestUnicast(Address receiver, Object value, ResponseMode responseMode) throws Exception
    {
        Message message = new Message(receiver, value);

        RequestOptions options = new RequestOptions();
        options.setMode(responseMode);

        return this.dispatcher.sendMessage(message, options);
    }
}