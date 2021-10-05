package utility;

import utility.connection.RequestHandler;

import java.util.concurrent.RecursiveTask;

public class ProcessTask extends RecursiveTask<Response> {
    private final RequestHandler requestHandler;
    private final Request request;

    public ProcessTask(RequestHandler requestHandler, Request request) {
        this.requestHandler = requestHandler;
        this.request = request;
    }

    @Override
    protected Response compute() {
        return requestHandler.process(request);
    }
}
