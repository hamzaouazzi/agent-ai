package fr.ho.agentai.controller;

import fr.ho.agentai.agent.AIAgent;
import fr.ho.agentai.rag.DocumentIndexor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;


@RestController
@CrossOrigin(origins = "*")
public class AgentController {
    private AIAgent aiAgent;
    private DocumentIndexor indexor;

    public AgentController(AIAgent aiAgent, DocumentIndexor indexor) {
        this.aiAgent = aiAgent;
        this.indexor = indexor;
    }

    @GetMapping(value = "/askAgent", produces = MediaType.TEXT_PLAIN_VALUE)
    public Flux<String> askAgent(@RequestParam(defaultValue = "Hey") String query) {
        return aiAgent.onQuery(query);
    }

    @PostMapping(value = "/loadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void loadFile(@RequestPart("file") MultipartFile file) {
        indexor.loadFile(file);
    }


}
