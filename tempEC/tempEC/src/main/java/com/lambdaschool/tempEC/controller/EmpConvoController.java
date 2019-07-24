package com.lambdaschool.tempEC.controller;

import com.twilio.sdk.*;
import com.twilio.sdk.resource.factory.*;
import io.swagger.annotations.*;
import com.lambdaschool.tempEC.models.Conversation;
import com.lambdaschool.tempEC.models.ErrorDetail;
import com.lambdaschool.tempEC.services.ConversationService;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("")
public class EmpConvoController {

    @Autowired
    private ConversationService convoService;

    @ApiOperation(value = "return all conversations", response = Conversation.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integr", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})
    @GetMapping(value="/conversations")
    public ResponseEntity<?> findAllConvos() {
        return new ResponseEntity<>(convoService.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Create new conversation.", response=Conversation.class)
    @ApiResponses(value = {
            @ApiResponse(code=201, message = "Author book added successfully", response = Conversation.class),
            @ApiResponse(code=404, message = "Incorrect endpoint.", response = EntityNotFoundException.class),
            @ApiResponse(code=500, message = "Invalid request body.", response = ErrorDetail.class)
    })
    @PostMapping(value="/conversations")
    public ResponseEntity<?> createNewConversation(@Valid @RequestBody Conversation newConvo) {
        Conversation createdConvo = convoService.save(newConvo);
        String ACCOUNT_SID = "AC3004f5000c8be4bc0f62a04d63d5b6d0";
        String AUTH_TOKEN = "5c3b289f7685463d37294c16208a6512";
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("To", newConvo.getFfnumber()));
        params.add(new BasicNameValuePair("From", "+19179206969"));
        params.add(new BasicNameValuePair("Body", "Someone you know would like to speak with you about a sensitive matter. " + "https://empowered-conversation.netlify.com/conversation/resources/" + "?cid=" + createdConvo.getConversationid()));
        MessageFactory messageFactory = client.getAccount().getMessageFactory();
        try { messageFactory.create(params); } catch(Exception exc) { System.out.println(exc); };
        return new ResponseEntity<>(createdConvo, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Deletes conversation.", response=String.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "Conversation deleted successfully", response = String.class),
            @ApiResponse(code=404, message = "Conversation ID not valid.", response = EntityNotFoundException.class),
    })
    @DeleteMapping(value="/conversations/finished/{conversationid}")
    public ResponseEntity<?> finishConversation(@PathVariable long conversationid) {
        ArrayList<Conversation> list = new ArrayList<>();
        convoService.findAll().iterator().forEachRemaining(list::add);
        for(Conversation c : list) {
            if(c.getConversationid() == conversationid) {
                break;
            } else if(c.getConversationid() >= conversationid) {
                return new ResponseEntity<>("Conversation already deleted", HttpStatus.OK);
            } else if(list.get(list.size()-1).getConversationid() < conversationid) {
                return new ResponseEntity<>("Conversation already deleted or doesn't exist", HttpStatus.OK);
            }
        }
        Conversation createdConvo = convoService.findById(conversationid);
        String ACCOUNT_SID = "AC3004f5000c8be4bc0f62a04d63d5b6d0";
        String AUTH_TOKEN = "5c3b289f7685463d37294c16208a6512";
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("To", createdConvo.getSurvivornumber()));
        params.add(new BasicNameValuePair("From", "+19179206969"));
        params.add(new BasicNameValuePair("Body", createdConvo.getFfname() + " is ready to speak with you and has read resources to prepare themselves. Thank you for trusting in the Empowered Conversations service."));
        MessageFactory messageFactory = client.getAccount().getMessageFactory();
        try { messageFactory.create(params); convoService.delete(conversationid); } catch(Exception exc) { throw new EntityNotFoundException(exc.toString()); };
        return new ResponseEntity<>("Conversation " + conversationid + " has been closed.", HttpStatus.OK);
    }
}
