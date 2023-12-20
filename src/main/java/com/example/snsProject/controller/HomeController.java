package com.example.snsProject.controller;

import com.example.snsProject.model.DTO.CommentViewDTO;
import com.example.snsProject.model.DTO.PostAllDTO;
import com.example.snsProject.repository.Emoticon;
import com.example.snsProject.service.FollowService;
import com.example.snsProject.service.MemberService;
import com.example.snsProject.service.PageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class HomeController {
    private final FollowService followService;
    private final PageService pageService;
    private final MemberService memberService;
    private List<PostAllDTO> posts;
    private final Emoticon emoticon;

    @GetMapping
    public String infiniteScrollPage(Model model) {
        return "infinite-scroll";
    }

    @GetMapping("/load-more-data")
    public String loadMoreData(@AuthenticationPrincipal UserDetails user, @RequestParam int nextPageLimit, @RequestParam int limit) {
        String htmlsPost = "";
        String htmlPost = "";
        try {
            posts = pageService.getPosts(user.getUsername(),nextPageLimit,nextPageLimit);
            for (PostAllDTO post: posts) {
                htmlPost = " <article id=\""+"Post"+post.getId()+"\">\n" +
                        "<div class=\"home_feed_contents\">\n" +
                        "<div class=\"home_feed_head\">\n" +
                        "<div class=\"feed_head_box\">\n" +
                        "<div class=\"feed_head_img_box1\">\n" +
                        "<div>\n" +
                        "<div class=\"feed_head_img_box2\">\n" +
                        "<a href=\"#\" class=\"feed_head_img\">\n" +
                        "<img alt=\"누군가의 프로필 사진\" class=\"feed_head_profile_img\" src=\""+post.getUrl()+"\" onerror=\"this.src='/img/logo.png'\">\n" +
                        "</a>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "<div class=\"feed_head_text_box1\">\n" +
                        "<div class=\"feed_head_text_box2\">\n" +
                        "<div>\n" +
                        "<div class=\"feed_head_nameInfo\">\n" +
                        "<a href=\"#\" class=\"feed_head_nameLink\">\n" +
                        "<span class=\"feed_head_name\">"+post.getName()+"</span>\n" +
                        "</a>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "<div class=\"feed_head_timeInfo\">\n" +
                        "<span class=\"dot_for_separate\">\n" +
                        "<span class=\"common_dot\">•</span>\n" +
                        "</span>\n" +
                        "<div class=\"feed_head_time_box\">\n" +
                        "<a class=\"feed_head_timeLink\">\n" +
                        "<span class=\"feed_head_time\">"+post.getDate()+"</span>\n" +
                        "</a>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "<div class=\"feed_head_more_box1\">\n" +
                        "<div class=\"feed_head_more_box2\">\n" +
                        "<button type=\"button\" class=\"feed_head_morePopup\" data-bs-toggle=\"modal\" data-bs-target=\"#exampleModal\">\n" +
                        "<i class=\"fa-solid fa-ellipsis\"></i>\n" +
                        "</button>\n" +
                        "<div class=\"modal\" id=\"exampleModal\" tabindex=\"-1\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">\n" +
                        "<div class=\"modal-dialog modal-dialog-centered\" id=\"menu_align\">\n" +
                        "<div class=\"none\">\n" +
                        "<div class=\"modal_content menu_list_align\">\n" +
                        "<button type=\"button\" class=\"btn_modal\" data-bs-dismiss=\"modal\" aria-label=\"\">삭제</button>\n" +
                        "<button type=\"button\" class=\"btn_modal1\" data-bs-dismiss=\"modal\" aria-label=\"\">좋아요 수 숨기기</button>\n" +
                        "<button type=\"button\" class=\"btn_modal1\" data-bs-dismiss=\"modal\" aria-label=\"\">댓글 기능 해제</button>\n" +
                        "<button type=\"button\" class=\"btn_modal1\" data-bs-dismiss=\"modal\" aria-label=\"\">게시물로 이동</button>\n" +
                        "<button type=\"button\" class=\"btn_modal1\" data-bs-dismiss=\"modal\" aria-label=\"\">링크 복사</button>\n" +
                        "<button type=\"button\" class=\"btn_modal1\" data-bs-dismiss=\"modal\" aria-label=\"\">이 계정 정보</button>\n" +
                        "<button type=\"button\" class=\"btn_modal1\" data-bs-dismiss=\"modal\" aria-label=\"Close\">취소</button>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "</div>" +
                        "</div>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "<div class=\"home_feed_body\">\n" +
                        "<div class=\"feed_body_cont\">\n" +
                        "<div class=\"feed_body_list\">"+
                        "<div id=\"carouselExampleIndicators"+post.getId()+"\" class=\"carousel slide\">"+
                        "<div class=\"carousel-indicators\">";
                for (int i = 0; i < post.getImages().size(); i++) {
                    htmlPost += "<button type=\"button\" data-bs-target=\"#carouselExampleIndicators" + post.getId() + "\"" +
                            " data-bs-slide-to=\"" + i + "\"" +
                            (i == 0 ? " class=\"active\"" : "") +
                            " aria-label=\"Slide " + (i + 1) + "\">" +
                            "</button>";
                }
                htmlPost +="</div>   <div class=\"carousel-inner\"> " ;
                for(int i=0;i<post.getImages().size();i++) {
                    htmlPost += "<div "+(i==0 ? " class=\"carousel-item active\">" : "class=\"carousel-item\">") +
                            " <img class=\"carouselImg\" src=\""+ post.getImages().get(i).getUrl()+"\" alt=\"\" onerror=\"this.src='/img/logo.png'\"/>" +
                            "</div>";
                }
                htmlPost +="</div><button class=\"carousel-control-prev\" type=\"button\" data-bs-target=\"#carouselExampleIndicators"+post.getId()+"\" data-bs-slide=\"prev\">\n" +
                        "                                                <span class=\"carousel-control-prev-icon\" aria-hidden=\"true\"></span>\n" +
                        "                                                <span class=\"visually-hidden\">Previous</span>\n" +
                        "                                             </button>\n" +
                        "                                             <button class=\"carousel-control-next\" type=\"button\" data-bs-target=\"#carouselExampleIndicators"+post.getId()+"\" data-bs-slide=\"next\">\n" +
                        "                                                <span class=\"carousel-control-next-icon\" aria-hidden=\"true\"></span>\n" +
                        "                                                <span class=\"visually-hidden\">Next</span>\n" +
                        "                                             </button>\n" +
                        "                                          </div>\n" +
                        "                                       </div>\n" +
                        "                                    </div>\n" +
                        "                                 </div>"+
                        "                               <div class=\"home_feed_footer\">\n" +
                                    "                                       <div class=\"feed_footer_cont\">\n" +
                                    "                                          <div class=\"feed_footer_actionBox\">\n" +
                                    "                                             <div class=\"feed_footer_interactive\">\n" +
                                    "                                                <div class=\"feed_footer_likeBox def_size\">\n" +
                                    "                                                   <span class=\"like-icon\"><i class=\"" + (post.getLikeYN() == 1 ?  "fa-solid fa-heart\"" : "fa-regular fa-heart\"") +" data-post-id = \""+post.getId()+"\"></i></span>\n" +
                                    "                                                </div>\n" +
                                    "                                                <div class=\"feed_footer_commentBox def_size\">\n" +
                                    "                                                   <span><i class=\"fa-regular fa-comment fa-lg\" data-post-id = \""+post.getId()+"\"></i></span>\n" +
                                    "                                                </div>\n" +
                                    "                                                <div class=\"feed_footer_messageBox def_size\">\n" +
                                    "                                                   <span><i class=\"fa-regular fa-paper-plane fa-lg\" data-post-id = \""+post.getId()+"\"></i></span>\n" +
                                    "                                                </div>\n" +
                                    "                                             </div>\n" +
                                    "                                             <div class=\"feed_footer_bookmarkBox\">\n" +
                                    "                                                <div class=\"feed_footer_bookmark def_size\">\n" +
                                    "                                                   <span><i class=\""+(post.getBookmarkYN() > 0 ? "fa-solid fa-bookmark" : "fa-regular fa-bookmark" ) +"\"data-post-id = \""+post.getId()+"\"></i></span>\n" +
                                    "                                                </div>\n" +
                                    "                                             </div>\n" +
                                    "                                          </div>\n" +
                                    "                                          <div class=\"feed_footer_likeNumCont\">\n" +
                                    "                                             <div class=\"feed_footer_likeNumBox\">\n" +
                                    "                                                <span class=\"feed_footer_likeNumText\">\n" +
                                    "                                                   좋아요\n" +
                                    "                                                   <span class=\"feed_footer_likeNum\" id= \""+ "likeCount_"+post.getId().toString()+"\">"+post.getLikes().size()+"</span>만개\n" +
                                    "                                                </span>\n" +
                                    "                                             </div>\n" +
                                    "                                          </div>\n" +
                                    "                                          <div class=\"feed_footer_ownerCommentBox\">\n" +
                                    "                                             <div class=\"feed_footer_ownerNameBox\">\n" +
                                    "                                                <span class=\"feed_footer_ownerName\">\n" + post.getUserName()+
                                    "                                                </span>\n" +
                                    "                                             </div>\n" +
                                    "                                             <div class=\"feed_footer_ownerTextBox\">\n" +
                                    "                                                <span class=\"feed_footer_ownerText\">\n" + (post.getContent() != null ? post.getContent() : "") +
                                    "                                                </span>\n" +
                                    "                                             </div>\n" +
                                    "                                          </div>\n" +
                                    "                                          <div class=\"feed_footer_commentNumCont\">\n" +
                                    "                                             <div class=\"feed_footer_commentNumBox\">\n" +
                                    "                                                <span class=\"feed_footer_commentText\">\n" +
                                    "                                                   댓글\n" +
                                    "                                                   <span class=\"feed_footer_commentNum\">"+post.getCommentSize()+"</span>개" +
                                    "<button type=\"button\" class=\"modal_comment_btn\" data-bs-toggle=\"modal\" data-bs-target=\"#exampleModal" + post.getId()+"\"> \n" +
                        "                                                       모두 보기\n" +
                        "                                                   </button>"+
                                    "                                                </span>\n" +
                                    "                                             </div>\n" +
                                    "                                          </div>\n" +
                                    "                                          <div class=\"feed_footer_inputCont\">\n" +
                                    "                                             <form>\n" +
                                    "                                                <div class=\"feed_footer_inputBox\">\n" +
                                    "                                                   <div class=\"feed_footer_postCommentBox\">\n" +
                                    "                                                      <input type=\"text\" class=\"feed_footer_postComment1\" placeholder=\"댓글 달기..\">\n" +
                                    "                                                      <input type=\"hidden\" value=\""+post.getId()+"\">\n" +
                                    "                                                   </div>\n" +
                                    "                                                   <div class=\"feed_footer_emoteButton\">\n" +
                                    "<span>\n" +
                        "                                                      <i class=\"fa-regular fa-face-smile\">\n" +
                        "                                                      </i>\n" +
                        "                                                      <div class=\"emoticonbox\">\n";
                for (String emoticon:emoticon.getEmoticons()) {
                    htmlPost += "<div class=\"emoticon\">"+emoticon+"</div>\n";
                }
                        htmlPost += "                                                      </div>\n" +
                        "                                                   </span>\n" +
                        "                                                </div>\n" +
                        "                                             </div>\n" +
                        "                                          </form>\n" +
                        "                                       </div>\n" +
                        "                                    </div>\n" +
                        "                                 </div>\n" +
                        "                              </div>\n" +
                        "                              <div class=\"modal\" id=\"exampleModal"+post.getId()+"\" tabindex=\"-1\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">\n" +
                        "                                 <div class=\"modal-dialog modal-dialog-centered\">\n" +
                        "                                    <div class=\"none\">\n" +
                        "                                       <div class=\"modal-content\">\n" +
                        "                                          <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>\n" +
                        "                                          <div class=\"modal-body\">\n" +
                        "                                             <div id=\"carouselExampleIndicators_"+post.getId()+"\" class=\"carousel slide\">\n" +
                        "                                                <div class=\"carousel-indicators\">\n";
                for (int i = 0; i < post.getImages().size(); i++) {
                    htmlPost += "<button type=\"button\" data-bs-target=\"#carouselExampleIndicators_" + post.getId() + "\"" +
                            " data-bs-slide-to=\"" + i + "\"" +
                            (i == 0 ? " class=\"active\"" : "") +
                            " aria-label=\"Slide " + (i + 1) + "\">" +
                            "</button>";
                }

        htmlPost+=      "                                    </div>\n" +
                        "                                                <div class=\"carousel-inner\">\n" ;
                for (int i = 0; i < post.getImages().size(); i++) {
                    htmlPost += "<div "+(i==0 ? " class=\"carousel-item active\">" : "class=\"carousel-item\">") +
                            "<img class=\"carouselImg\" src=\""+post.getImages().get(i).getUrl()+"\" alt=\"\" onerror=\"this.src='/img/logo.png'\"/>"+
                            "</div>";
                }
                       htmlPost += "                                                </div>" +
                        "                                                <button class=\"carousel-control-prev\" type=\"button\" data-bs-target=\"#carouselExampleIndicators_"+post.getId()+"\" " +
                        "                                                        data-bs-slide=\"prev\">\n" +
                        "                                                   <span class=\"carousel-control-prev-icon\" aria-hidden=\"true\"></span>\n" +
                        "                                                   <span class=\"visually-hidden\">Previous</span>\n" +
                        "                                                </button>\n" +
                        "                                                <button class=\"carousel-control-next\" type=\"button\" data-bs-target=\"#carouselExampleIndicators_"+post.getId()+"\" " +
                        "                                                        data-bs-slide=\"next\">\n" +
                        "                                                   <span class=\"carousel-control-next-icon\" aria-hidden=\"true\"></span>\n" +
                        "                                                   <span class=\"visually-hidden\">Next</span>\n" +
                        "                                                </button>\n" +
                        "\n" +
                        "                                             </div>\n" +
                        "                                             <div class=\"explore_feed_contents\">\n" +
                        "                                                <header class=\"explore_feed_head\">\n" +
                        "                                                   <div class=\"explore_feed_head_box\">\n" +
                        "\n" +
                        "                                                      <div class=\"explore_feed_head_imgbox\">\n" +
                        "                                                         <a href=\"#\" class=\"explore_feed_head_img\">\n" +
                        "                                                            <img src=\""+post.getUrl()+"\" alt=\"\" onerror=\"this.src='/img/logo.png'\">\n" +
                        "                                                         </a>\n" +
                        "                                                      </div>\n" +
                        "\n" +
                        "                                                      <div class=\"explore_feed_infobox\">\n" +
                        "                                                         <div class=\"explore_feed_infobox1\" >\n" + post.getName()+
                        "                                                         </div>\n" +
                        "                                                         <div class=\"explore_feed_infobox2\" >\n" + post.getUserName()+
                        "                                                         </div>\n" +
                        "                                                      </div>\n" +
                        "\n" +
                        "                                                   </div>\n" +
                        "                                                   <div class=\"explore_feed_head_ellipsis\">\n" +
                        "                                                      <i class=\"fa-solid fa-ellipsis fa-lg\"></i>\n" +
                        "                                                   </div>\n" +
                        "                                                </header>\n" +
                        "\n" +
                        "                                                <div class=\"explore_feed_inside\">\n" +
                        "                                                   <div class=\"explore_feed_scrolls\">\n" +
                        "                                                       <div class=\"explore_feed_head_box\">\n" +
                        "                                                           <div class=\"explore_feed_head_imgbox\">\n" +
                        "                                                               <a href=\"#\" class=\"explore_feed_head_img\">\n" +
                        "                                                                    <img src=\""+post.getUrl()+"\" alt=\"\" onerror=\"this.src='/img/logo.png'\">\n" +
                        "                                                               </a>\n" +
                        "                                                           </div>\n" +
                        "                                                           <div class=\"explore_feed_infobox\">\n" +
                        "                                                               <div class=\"explore_feed_infobox1\" >\n" + post.getName() +
                        "                                                               </div>\n" +
                        "                                                               <div class=\"explore_feed_infobox2\" >\n" + (post.getUserName() != null ? post.getUserName() : "")+
                        "                                                               </div>\n" +
                        "                                                           </div>\n" +
                        "                                                       </div>\n";
                for (CommentViewDTO postComment:post.getComments()) {
                    htmlPost += "<div class=\"explore_feed_comment_box\">"+
                            "<div class=\"explore_feed_comment_imgbox\">" +
                            "<a href=\"#\" class=\"explore_feed_comment_img\">" +
                            "<img src=\""+postComment.getUrl()+"\" alt=\"\" onerror=\"this.src='/img/logo.png'\">" +
                            "</a>" +
                            "</div>" +
                            "<div class=\"explore_feed_comment_infobox\">" +
                            "<div class=\"explore_feed_comment_infobox1\">" + postComment.getName() +
                            "</div>" +
                            "<div class=\"explore_feed_comment_infobox2\" >" + postComment.getContent() +
                            "</div>" +
                            "<div class=\"explore_feed_comment_infobox2\" >" + postComment.getDate() +
                            "</div>" +
                            "</div>" +
                            "</div>";
                }
                htmlPost +=      "</div><div class=\"explore_feed_footer\">\n" +
                        "                                                      <div class=\"home_feed_footer\">\n" +
                        "                                                         <div class=\"feed_footer_cont\">\n" +
                        "                                                            <div class=\"feed_footer_actionBox\">\n" +
                        "                                                               <div class=\"feed_footer_interactive\">\n" +
                        "                                                                  <div class=\"feed_footer_likeBox def_size\">\n" +
                        "                                                    <span class=\"like-icon\">\n" +
                        "  <i class=" + (post.getLikeYN() == 1 ?  "fa-solid fa-heart" : "fa-regular fa-heart") + " data-post-id = \""+post.getId()+"\"></i>"+
                        "                                                    </span>\n" +
                        "                                                                  </div>\n" +
                        "                                                                  <div class=\"feed_footer_commentBox def_size\">\n" +
                        "                                                                     <span><i class=\"fa-regular fa-comment fa-lg\" data-post-id = \""+post.getId()+"\"></i></span>\n" +
                        "                                                                  </div>\n" +
                        "                                                                  <div class=\"feed_footer_messageBox def_size\">\n" +
                        "                                                                     <span><i class=\"fa-regular fa-paper-plane fa-lg\" data-post-id = \""+post.getId()+"\"></i></span>\n" +
                        "                                                                  </div>\n" +
                        "                                                               </div>\n" +
                        "                                                               <div class=\"feed_footer_bookmarkBox\">\n" +
                        "                                                                  <div class=\"feed_footer_bookmark def_size\">\n" +
                        "<span><i class=\""+(post.getBookmarkYN() > 0 ? "fa-solid fa-bookmark" : "fa-regular fa-bookmark" ) +"\"data-post-id = \""+post.getId()+"\"></i></span>\n" +
                        "                                                                  </div>\n" +
                        "                                                               </div>\n" +
                        "                                                            </div>\n" +
                        "                                                            <div class=\"feed_footer_likeNumCont\">\n" +
                        "                                                               <div class=\"feed_footer_likeNumBox\">\n" +
                        "                                                    <span class=\"feed_footer_likeNumText\">\n" +
                        "                                                       좋아요\n" +
                        "                                                       <span class=\"feed_footer_likeNum\" id=\"likeCount2_" +post.getId()+"\" > "+ (post.getLikes() != null ? post.getLikes().size() : 0)+ "</span>만개\n" +
                        "                                                    </span>\n" +
                        "                                                               </div>\n" +
                        "                                                            </div>\n" +
                        "                                                            <div class=\"feed_footer_ownerCommentBox\">\n" +
                        "                                                               <div class=\"feed_footer_ownerNameBox\">\n" +
                        "                                                    <span class=\"feed_footer_ownerName\"  text=\""+post.getUserName()+"\">\n" +
                        "                                                    </span>\n" +
                        "                                                               </div>\n" +
                        "                                                               <div class=\"feed_footer_ownerTextBox\">\n" +
                        "                                                    <span class=\"feed_footer_ownerText\"  text=\""+post.getContent()+"\">\n" +
                        "                                                    </span>\n" +
                        "                                                               </div>\n" +
                        "                                                            </div>\n" +
                        "                                                            <div class=\"feed_footer_commentNumCont\">\n" +
                        "                                                               <div class=\"feed_footer_commentNumBox\">\n" +
                        "                                                               </div>\n" +
                        "                                                            </div>\n" +
                        "                                                            <div class=\"feed_footer_inputCont\">\n" +
                        "                                                               <form>\n" +
                        "                                                                  <div class=\"feed_footer_inputBox\">\n" +
                        "                                                                     <div class=\"feed_footer_postCommentBox\">\n" +
                        "                                                                        <input type=\"text\" class=\"feed_footer_postComment2\" placeholder=\"댓글 달기..\">\n" +
                        "                                                                        <input type=\"hidden\" id = \"postId\" value=\""+post.getId()+"\">\n" +
                        "                                                                     </div>\n" +
                        "                                                                      <div class=\"feed_footer_emoteButton\">\n" +
                        "                                                                           <span>\n" +
                        "                                                                              <i class=\"fa-regular fa-face-smile\">\n" +
                        "                                                                              </i>\n" +
                        "                                                                              <div class=\"emoticonbox2\">\n";
                for (String emoticon:emoticon.getEmoticons()) {
                    htmlPost += "<div class=\"emoticon\" >"+emoticon+"</div>\n";
                }
        htmlPost +=     "                                                                              </div>\n" +
                        "                                                                           </span>\n" +
                        "                                                                      </div>\n" +
                        "                                                                  </div>\n" +
                        "                                                               </form>\n" +
                        "                                                            </div>\n" +
                        "                                                         </div>\n" +
                        "                                                      </div>\n" +
                        "                                                   </div>\n" +
                        "                                                </div>\n" +
                        "                                             </div>\n" +
                        "                                          </div>\n" +
                        "                                       </div>\n" +
                        "\n" +
                        "                                    </div>\n" +
                        "\n" +
                        "                                 </div>\n" +
                        "                              </div>\n" +
                        "                           </article>";
                htmlsPost += htmlPost;
                htmlPost = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return htmlsPost;
    }

    @RequestMapping("/followtest")
    public ResponseEntity<?> getFollowAllUser(@AuthenticationPrincipal UserDetails user) {
        String followList = "";
        try {
            followList = followService.followList(user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(followList);
    }
    @RequestMapping("/followtest2")
    public ResponseEntity<?> getFollowAllUser2(@AuthenticationPrincipal UserDetails user) {
        String test = "";
        try {
            posts = pageService.getPosts(user.getUsername(), 0, 8);
            for (PostAllDTO post: posts) {
                if(post != null && post.getId() != null) {
                String postIdString = Long.toString(post.getId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(test);
    }

    @GetMapping("/SearchUserResults")
    public ResponseEntity<Map<String, Object>> SearchUsers(@RequestParam String search_input) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("result", memberService.SearchUsers(search_input));
            response.put("success", true);
            response.put("message", "성공.");


        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "실패.");
        }
        return ResponseEntity.ok(response);
    }
}
