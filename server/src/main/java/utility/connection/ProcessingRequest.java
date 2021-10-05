package utility.connection;

import utility.ProcessTask;
import utility.Request;
import utility.Response;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

public class ProcessingRequest {
    private final RequestHandler requestHandler;
    private final ForkJoinPool processThreads;
    private final ExecutorService sendThreads;

    public ProcessingRequest(RequestHandler requestHandler, ForkJoinPool processThreads, ExecutorService sendThreads) {
        this.requestHandler = requestHandler;
        this.processThreads = processThreads;
        this.sendThreads = sendThreads;
    }

    public void process(Request request, Socket socket) {
        ProcessTask processTask = new ProcessTask(requestHandler, request);
        Response response = processThreads.invoke(processTask);
        ResponseSender responseSender = new ResponseSender(response, socket);
        sendThreads.execute(responseSender);
    }
}
