package com.elastic.search.controller;

import com.elastic.search.model.StudentModel;
import com.elastic.search.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Resource(name="studentService")
    private StudentService studentService;

    @RequestMapping("/list")
    @ResponseBody
    public List<StudentModel> list(){
        return studentService.findAll();
    }

    @RequestMapping("/add")
    @ResponseBody
    public boolean add(){
        return studentService.add();
    }

    @RequestMapping("/batchAdd")
    @ResponseBody
    public boolean batchAdd(){
        return studentService.batchAdd();
    }

    @RequestMapping("/update")
    @ResponseBody
    public Object update(){
        return studentService.update();
    }

    @RequestMapping("/delete/{id}")
    @ResponseBody
    public String deleteById(@PathVariable("id") String id){
        return studentService.delete(id);
    }


    @RequestMapping("/singleWord")
    @ResponseBody
    public Object singleWord(String word, @PageableDefault() Pageable pageable) {
        return studentService.singleWord(word,pageable);
    }

    @RequestMapping("/singleWordOrder")
    @ResponseBody
    public Object singleWordOrder(String word, @PageableDefault(sort = "age", direction = Sort.Direction.DESC) Pageable pageable) {
        return studentService.singleWord(word,pageable);
    }

    @RequestMapping(value="/singleMatch",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object singleMatch(String key, String value, @PageableDefault Pageable pageable) {
        return studentService.singleMatch(key,value,pageable);
    }


    @RequestMapping(value="/singlePhraseMatch")
    @ResponseBody
    public Object singlePhraseMatch(String word, @PageableDefault Pageable pageable) {
        return studentService.singlePhraseMatch(word,pageable);
    }

    @RequestMapping(value="/singleTerm")
    @ResponseBody
    public Object singleTerm(String id, String interest ,@PageableDefault Pageable pageable) {
        return studentService.singleTerm(id,interest,pageable);
    }


    @RequestMapping(value="/multiMatch")
    @ResponseBody
    public Object multiMatch( String content ,@PageableDefault Pageable pageable) {
        return studentService.multiMatch(content,pageable);
    }

    @RequestMapping(value="/contain")
    @ResponseBody
    public Object contain( String content ,@PageableDefault Pageable pageable) {
        return studentService.contain(content,pageable);
    }

    @RequestMapping(value="/containPercent")
    @ResponseBody
    public Object containPercent( String content ,@PageableDefault Pageable pageable) {
        return studentService.containPercent(content,pageable);
    }
}
