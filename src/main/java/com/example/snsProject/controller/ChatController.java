package com.example.snsProject.controller;

import com.example.snsProject.repository.Emoticon;
import com.example.snsProject.service.ChatService;
import com.example.snsProject.service.EmoticonService;
import com.example.snsProject.service.MemberService;
import com.example.snsProject.service.MessageService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final MessageService messageService;
    private final EmoticonService emoticonService;
    private final MemberService memberService;

    @PostMapping("/view/chat")
    public String chatpost(@RequestParam Map<String, Object> map,
                           @AuthenticationPrincipal UserDetails user,
                           HttpSession session,
                           Model model){
        long result = 0;                                                                               // 채팅방이 존재하는지 보는 플래그
        List<Map<String, Object>> getMessage = null;
        long room_num = 0;
        long room_id = 0;
        model.addAttribute("emoticon", emoticonService.selectEmoticonModule(memberService.findUser(user.getUsername()).getEmoticon()));
        model.addAttribute("user", memberService.findUser(user.getUsername()));
        if(map.get("target_id") == "" && map.get("content") == ""){

        }
        else if(map.get("target_id") == "") {                                                                // 엔터키를 통해 채팅을 전달 할 때
            long target_id = (long)session.getAttribute("target_id");
            room_id = (long)session.getAttribute("room_id");
            long message_id = ((long)session.getAttribute("message_id")) + 1;                        // 제일 마지막에 쓰인 메시지id + 1( = 이제 쓰일 메시지의 고유번호)

            messageService.insertMessage(Long.parseLong(user.getUsername()),room_id,message_id,4,map.get("content").toString()); // 자기자신, 채팅방 번호, 채팅방에서 이제 쓰일 메시지의 고유번호, 메시지내용

            room_num = (long)session.getAttribute("room_id");                                   // 채팅방의 고유번호
            getMessage = messageService.getAllMessage(room_num);                                    // 메세지 뷰의 전체 요소 가져오기
            model.addAttribute("getMessage",getMessage);                                // html 에 뿌리기
            model.addAttribute("target_id",target_id);
        }
        else if(map.get("content") == ""){                                                                   // 마우스 클릭으로 채팅방을 만들고 보여줄 때
            long target_id =  Integer.parseInt(map.get("target_id").toString());

            result = chatService.selectRoom(Long.parseLong(user.getUsername()),target_id);                                   // 만들어진 채팅방이 존재하는지 확인

            if(result == 0){                                                                        // 채팅방이 없는 경우
                chatService.insertRoom(Long.parseLong(user.getUsername()),target_id);                                        // 채팅방 만들기
            }

            room_id = chatService.getRoomId(Long.parseLong(user.getUsername()),target_id);                                   // 만들어진 채팅방의 고유번호 얻기
            session.setAttribute("room_id", room_id);
            // 채팅방의 채팅들을 다 가져오기
            room_num = (long)session.getAttribute("room_id");
            getMessage = messageService.getAllMessage(room_num);                                    // 해당 방의 message_view내용 모든 것을 가져옴

            long getMessageCount = messageService.getMessageCount();                                // 해당 방의 메시지 개수를 가져옴
            session.setAttribute("message_id",getMessageCount);                                  // 다음 메시지입력할 때 고유 id(= 해당방의 메시지 개수 (+1은 위에서 함))
            session.setAttribute("target_id",target_id);
            model.addAttribute("getMessage",getMessage);                                // 일단 전체 메시지 내용을 전달
            model.addAttribute("target_id",target_id);
        }
        return "response/chat :: .direct_list";
    }

    @GetMapping("/view/chat")
    public String chatget(@AuthenticationPrincipal UserDetails user, Model model, HttpSession session){
        model.addAttribute("emoticon", emoticonService.selectEmoticonModule(memberService.findUser(user.getUsername()).getEmoticon()));
        List<Map<String, Object>> result = null;
        //long member_id = (long)session.getAttribute("member_id");                                 // 세션에서 member_id 를 가져옴.
        model.addAttribute("user", memberService.findUser(user.getUsername()));
        result = chatService.getAllfollowers(Long.parseLong(user.getUsername()));
        model.addAttribute("result",result);

        List<Map<String, Object>> nresult = null;
        nresult = chatService.getAllNotfollowers(Long.parseLong(user.getUsername()));
        model.addAttribute("nresult",nresult);

        return "response/chat";
    }

}

