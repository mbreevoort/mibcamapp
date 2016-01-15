package nl.mib.cam;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CamImagesControllerTest {

    private MockMvc mockMvc;

    @Before
    public void setup() {
        File resourcesDirectory = new File("src/test/resources/camdir/");
        String path = resourcesDirectory.getAbsolutePath();

        CamImagesController camImagesController = new CamImagesController();
        camImagesController.setCamImagesRootDir("file://" + path + "/");

        this.mockMvc = MockMvcBuilders.standaloneSetup(camImagesController).build();
    }

    @Test
    public void listdirs() throws Exception {
        this.mockMvc.perform(get("/cam1/listdirs").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json("['20160115', '20160116']"));

    }

    @Ignore("generate objects for valid json")
    @Test
    public void listdirWithFiles() throws Exception {
        String dir = "20160115";
        this.mockMvc.perform(get("/cam1/list/" + dir).accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json("[{\"20160115\":[\"131540-01.jpg\", \"131541-00.jpg\", \"131541-01.jpg\"]}]"));

    }

    @Ignore("generate objects for valid json")
    @Test
    public void listdirWithoutFiles() throws Exception {
        String dir = "20160116";
        this.mockMvc.perform(get("/cam1/list/" + dir).accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json("[{\"20160116\":[]}]"));

    }
}
