package fr.ho.agentai.tool;

import org.springframework.ai.document.Document;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AgentTools {

    private VectorStore vectorStore;

    public AgentTools(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Tool(description = "Get employee info (name, salary, seniority)")
    public EmployeeInfo getEmployeeInfo(
            @ToolParam(description = "Employee name") String name) {
        return new EmployeeInfo(name, 100000.0, 10);
    }

    @Tool(description = "Get resume info of a person")
    public List<String> searchContext(String query) {
        List<Document> documents = vectorStore.similaritySearch(SearchRequest.builder()
                .query(query)
                // number of chunks
                .topK(4)
                .build());

        return documents
                .stream()
                .map(Document::getText)
                .collect(Collectors.toList());
    }
}


record EmployeeInfo(String name, double salary, int seniority) {}
