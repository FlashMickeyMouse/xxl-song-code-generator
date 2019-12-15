import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
* ${classInfo.classComment}
*
* Created by songhao on '${.now?string('yyyy-MM-dd HH:mm:ss')}'.
*/
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("${classInfo.className?uncap_first}")
public class ${classInfo.className}Controller extends SimpleCRUDController<${classInfo.className}Service, ${classInfo.className}> {


}