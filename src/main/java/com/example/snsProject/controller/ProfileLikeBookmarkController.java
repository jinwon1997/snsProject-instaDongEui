
package com.example.snsProject.controller;

import com.example.snsProject.model.DTO.BookmarkDTO;
import com.example.snsProject.model.DTO.PostAllDTO;
import com.example.snsProject.model.DTO.PostImageDTO;
import com.example.snsProject.service.BookmarkService;
import com.example.snsProject.service.PageService;
import com.example.snsProject.service.PostService;
import com.example.snsProject.service.ProfileDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ProfileLikeBookmarkController {
    private final PostService postService;
    private final PageService pageService;
    private final BookmarkService bookmarkService;

    private final ProfileDetailService profileDetailService;
    private List<PostAllDTO> postAllBookmarkDTO;
    private List<PostAllDTO> postAllDTO;
    @GetMapping("/registerBookmark")
    public ResponseEntity<Map<String, Object>> registerBookmark(@AuthenticationPrincipal UserDetails user, @RequestParam String postId) {
        Map<String, Object> response = new HashMap<>();
        BookmarkDTO bookmarkDTO = new BookmarkDTO(0, Long.parseLong(user.getUsername()),Long.parseLong(postId));
        try {
            if (bookmarkService.bookmarkYN(user.getUsername(), postId)  == 0 && bookmarkService.registerBookmark(bookmarkDTO)) {
                response.put("success", true);
                response.put("message", "북마크 등록 성공.");

                String htmlsPost = "";
                String htmlPost = "";
                try {
                    postAllBookmarkDTO = profileDetailService.getPostsBookmark(user.getUsername());
                    for (PostAllDTO post: postAllBookmarkDTO) {
                        htmlPost = " <article id=\""+"Post"+post.getId()+"\">\n" +
                                "                                 <div class=\"home_feed_contents\">\n" +
                                "                                    <div class=\"home_feed_head\">\n" +
                                "                                       <div class=\"feed_head_box\">\n" +
                                "                                          <div class=\"feed_head_img_box1\">\n" +
                                "                                             <div>\n" +
                                "                                                <div class=\"feed_head_img_box2\">\n" +
                                "                                                   <a href=\"#\" class=\"feed_head_img\">\n" +
                                "                                                      <img alt=\"프로필 사진\" class=\"feed_head_profile_img\" src=\""+post.getUrl()+"\">\n" +
                                "                                                   </a>\n" +
                                "                                                </div>\n" +
                                "                                             </div>\n" +
                                "                                          </div>\n" +
                                "                                          <div class=\"feed_head_text_box1\">\n" +
                                "                                             <div class=\"feed_head_text_box2\">\n" +
                                "                                                <div>\n" +
                                "                                                   <div class=\"feed_head_nameInfo\">\n" +
                                "                                                      <a href=\"#\" class=\"feed_head_nameLink\">\n" +
                                "                                                         <span class=\"feed_head_name\">"+post.getName()+"</span>\n" +
                                "                                                      </a>\n" +
                                "                                                   </div>\n" +
                                "                                                </div>\n" +
                                "                                                <div class=\"feed_head_timeInfo\">\n" +
                                "                                                   <span class=\"dot_for_separate\">\n" +
                                "                                                      <span class=\"common_dot\">•</span>\n" +
                                "                                                   </span>\n" +
                                "                                                   <div class=\"feed_head_time_box\">\n" +
                                "                                                      <a class=\"feed_head_timeLink\">\n" +
                                "                                                         <span class=\"feed_head_time\">"+post.getDate()+"</span>\n" +
                                "                                                      </a>\n" +
                                "                                                   </div>\n" +
                                "                                                </div>\n" +
                                "                                             </div>\n" +
                                "                                          </div>\n" +
                                "                                          <div class=\"feed_head_more_box1\">\n" +
                                "                                             <div class=\"feed_head_more_box2\">\n" +
                                "                                                <div class=\"feed_head_morePopup\">\n" +
                                "                                                   <i class=\"fa-solid fa-ellipsis\"></i>\n" +
                                "                                                </div>\n" +
                                "                                             </div>\n" +
                                "                                          </div>\n" +
                                "                                       </div>\n" +
                                "                                    </div>\n" +
                                "                                    <div class=\"home_feed_body\">\n" +
                                "                                       <div class=\"feed_body_cont\">\n" +
                                "                                          <div class=\"feed_body_list\">";
                        for (PostImageDTO image:post.getImages()) {
                            htmlPost += "<div class=\"feed_body_imgBox\" >\n" +
                                    "<a class=\"feed_body_imgLink\">\n" +
                                    "<img alt=\"#\" class=\"feed_body_img\" src=\"" + image.getUrl()+
                                    "\"></a>\n" +
                                    "</div>\n";
                        }
                        htmlPost += "  </div>\n" +
                                "                                       </div>\n" +
                                "                                    </div>\n" +
                                "                                    <div class=\"home_feed_footer\">\n" +
                                "                                       <div class=\"feed_footer_cont\">\n" +
                                "                                          <div class=\"feed_footer_actionBox\">\n" +
                                "                                             <div class=\"feed_footer_interactive\">\n" +
                                "                                                <div class=\"feed_footer_likeBox def_size\">\n" +
                                "                                                   <span class=\"like-icon\"><i class=\"" + (post.getLikeYN() == 1 ?  "fa-solid fa-heart\"" : "fa-regular fa-heart\"") +" data-post-id = \""+post.getId()+"\"" + "\"></i></span>\n" +
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
                                "                                                   <span class=\"feed_footer_commentNum\">"+post.getCommentSize()+"</span>개 모두 보기\n" +
                                "                                                </span>\n" +
                                "                                             </div>\n" +
                                "                                          </div>\n" +
                                "                                          <div class=\"feed_footer_inputCont\">\n" +
                                "                                             <form id=\""+post.getId()+"\">\n" +
                                "                                                <div class=\"feed_footer_inputBox\">\n" +
                                "                                                   <div class=\"feed_footer_postCommentBox\">\n" +
                                "                                                      <input type=\"text\" class=\"feed_footer_postComment\" placeholder=\"댓글 달기..\">\n" +
                                "                                                      <input type=\"hidden\" id = \"postId\" value=\""+post.getId()+"\">\n" +
                                "                                                   </div>\n" +
                                "                                                   <div class=\"feed_footer_emoteButton\">\n" +
                                "                                                      <span><i class=\"fa-regular fa-face-smile\"></i></span>\n" +
                                "                                                   </div>\n" +
                                "                                                </div>\n" +
                                "                                             </form>\n" +
                                "                                          </div>\n" +
                                "                                       </div>\n" +
                                "                                    </div>\n" +
                                "                                 </div>\n" +
                                "                              </article>";
                        htmlsPost += htmlPost;
                        htmlPost = "";



                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                response.put("htmlbookmark", htmlsPost); // 저장됨 탭
                htmlsPost = "";
                htmlPost = "";
                try {
                    postAllDTO = profileDetailService.getPosts(user.getUsername());
                    for (PostAllDTO post: postAllDTO) {
                        htmlPost = " <article id=\""+"Post"+post.getId()+"\">\n" +
                                "                                 <div class=\"home_feed_contents\">\n" +
                                "                                    <div class=\"home_feed_head\">\n" +
                                "                                       <div class=\"feed_head_box\">\n" +
                                "                                          <div class=\"feed_head_img_box1\">\n" +
                                "                                             <div>\n" +
                                "                                                <div class=\"feed_head_img_box2\">\n" +
                                "                                                   <a href=\"#\" class=\"feed_head_img\">\n" +
                                "                                                      <img alt=\"프로필 사진\" class=\"feed_head_profile_img\" src=\""+post.getUrl()+"\">\n" +
                                "                                                   </a>\n" +
                                "                                                </div>\n" +
                                "                                             </div>\n" +
                                "                                          </div>\n" +
                                "                                          <div class=\"feed_head_text_box1\">\n" +
                                "                                             <div class=\"feed_head_text_box2\">\n" +
                                "                                                <div>\n" +
                                "                                                   <div class=\"feed_head_nameInfo\">\n" +
                                "                                                      <a href=\"#\" class=\"feed_head_nameLink\">\n" +
                                "                                                         <span class=\"feed_head_name\">"+post.getName()+"</span>\n" +
                                "                                                      </a>\n" +
                                "                                                   </div>\n" +
                                "                                                </div>\n" +
                                "                                                <div class=\"feed_head_timeInfo\">\n" +
                                "                                                   <span class=\"dot_for_separate\">\n" +
                                "                                                      <span class=\"common_dot\">•</span>\n" +
                                "                                                   </span>\n" +
                                "                                                   <div class=\"feed_head_time_box\">\n" +
                                "                                                      <a class=\"feed_head_timeLink\">\n" +
                                "                                                         <span class=\"feed_head_time\">"+post.getDate()+"</span>\n" +
                                "                                                      </a>\n" +
                                "                                                   </div>\n" +
                                "                                                </div>\n" +
                                "                                             </div>\n" +
                                "                                          </div>\n" +
                                "                                          <div class=\"feed_head_more_box1\">\n" +
                                "                                             <div class=\"feed_head_more_box2\">\n" +
                                "                                                <div class=\"feed_head_morePopup\">\n" +
                                "                                                   <i class=\"fa-solid fa-ellipsis\"></i>\n" +
                                "                                                </div>\n" +
                                "                                             </div>\n" +
                                "                                          </div>\n" +
                                "                                       </div>\n" +
                                "                                    </div>\n" +
                                "                                    <div class=\"home_feed_body\">\n" +
                                "                                       <div class=\"feed_body_cont\">\n" +
                                "                                          <div class=\"feed_body_list\">";
                        for (PostImageDTO image:post.getImages()) {
                            htmlPost += "<div class=\"feed_body_imgBox\" >\n" +
                                    "<a class=\"feed_body_imgLink\">\n" +
                                    "<img alt=\"#\" class=\"feed_body_img\" src=\"" + image.getUrl()+
                                    "\"></a>\n" +
                                    "</div>\n";
                        }
                        htmlPost += "  </div>\n" +
                                "                                       </div>\n" +
                                "                                    </div>\n" +
                                "                                    <div class=\"home_feed_footer\">\n" +
                                "                                       <div class=\"feed_footer_cont\">\n" +
                                "                                          <div class=\"feed_footer_actionBox\">\n" +
                                "                                             <div class=\"feed_footer_interactive\">\n" +
                                "                                                <div class=\"feed_footer_likeBox def_size\">\n" +
                                "                                                   <span class=\"like-icon\"><i class=\"" + (post.getLikeYN() == 1 ?  "fa-solid fa-heart\"" : "fa-regular fa-heart\"") +" data-post-id = \""+post.getId()+"\"" + "\"></i></span>\n" +
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
                                "                                                   <span class=\"feed_footer_commentNum\">"+post.getCommentSize()+"</span>개 모두 보기\n" +
                                "                                                </span>\n" +
                                "                                             </div>\n" +
                                "                                          </div>\n" +
                                "                                          <div class=\"feed_footer_inputCont\">\n" +
                                "                                             <form id=\""+post.getId()+"\">\n" +
                                "                                                <div class=\"feed_footer_inputBox\">\n" +
                                "                                                   <div class=\"feed_footer_postCommentBox\">\n" +
                                "                                                      <input type=\"text\" class=\"feed_footer_postComment\" placeholder=\"댓글 달기..\">\n" +
                                "                                                      <input type=\"hidden\" id = \"postId\" value=\""+post.getId()+"\">\n" +
                                "                                                   </div>\n" +
                                "                                                   <div class=\"feed_footer_emoteButton\">\n" +
                                "                                                      <span><i class=\"fa-regular fa-face-smile\"></i></span>\n" +
                                "                                                   </div>\n" +
                                "                                                </div>\n" +
                                "                                             </form>\n" +
                                "                                          </div>\n" +
                                "                                       </div>\n" +
                                "                                    </div>\n" +
                                "                                 </div>\n" +
                                "                              </article>";
                        htmlsPost += htmlPost;
                        htmlPost = "";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                response.put("htmlpost", htmlsPost); // 게시물 탭

            } else if(bookmarkService.bookmarkYN(user.getUsername(), postId)  > 0 && bookmarkService.deleteBookmark(user.getUsername(), postId)){
                response.put("success", true);
                response.put("message", "북마크 삭제 완료.");

                String htmlsPost = "";
                String htmlPost = "";
                try {
                    postAllBookmarkDTO = profileDetailService.getPostsBookmark(user.getUsername());
                    for (PostAllDTO post: postAllBookmarkDTO) {
                        htmlPost = " <article id=\""+"Post"+post.getId()+"\">\n" +
                                "                                 <div class=\"home_feed_contents\">\n" +
                                "                                    <div class=\"home_feed_head\">\n" +
                                "                                       <div class=\"feed_head_box\">\n" +
                                "                                          <div class=\"feed_head_img_box1\">\n" +
                                "                                             <div>\n" +
                                "                                                <div class=\"feed_head_img_box2\">\n" +
                                "                                                   <a href=\"#\" class=\"feed_head_img\">\n" +
                                "                                                      <img alt=\"프로필 사진\" class=\"feed_head_profile_img\" src=\""+post.getUrl()+"\">\n" +
                                "                                                   </a>\n" +
                                "                                                </div>\n" +
                                "                                             </div>\n" +
                                "                                          </div>\n" +
                                "                                          <div class=\"feed_head_text_box1\">\n" +
                                "                                             <div class=\"feed_head_text_box2\">\n" +
                                "                                                <div>\n" +
                                "                                                   <div class=\"feed_head_nameInfo\">\n" +
                                "                                                      <a href=\"#\" class=\"feed_head_nameLink\">\n" +
                                "                                                         <span class=\"feed_head_name\">"+post.getName()+"</span>\n" +
                                "                                                      </a>\n" +
                                "                                                   </div>\n" +
                                "                                                </div>\n" +
                                "                                                <div class=\"feed_head_timeInfo\">\n" +
                                "                                                   <span class=\"dot_for_separate\">\n" +
                                "                                                      <span class=\"common_dot\">•</span>\n" +
                                "                                                   </span>\n" +
                                "                                                   <div class=\"feed_head_time_box\">\n" +
                                "                                                      <a class=\"feed_head_timeLink\">\n" +
                                "                                                         <span class=\"feed_head_time\">"+post.getDate()+"</span>\n" +
                                "                                                      </a>\n" +
                                "                                                   </div>\n" +
                                "                                                </div>\n" +
                                "                                             </div>\n" +
                                "                                          </div>\n" +
                                "                                          <div class=\"feed_head_more_box1\">\n" +
                                "                                             <div class=\"feed_head_more_box2\">\n" +
                                "                                                <div class=\"feed_head_morePopup\">\n" +
                                "                                                   <i class=\"fa-solid fa-ellipsis\"></i>\n" +
                                "                                                </div>\n" +
                                "                                             </div>\n" +
                                "                                          </div>\n" +
                                "                                       </div>\n" +
                                "                                    </div>\n" +
                                "                                    <div class=\"home_feed_body\">\n" +
                                "                                       <div class=\"feed_body_cont\">\n" +
                                "                                          <div class=\"feed_body_list\">";
                        for (PostImageDTO image:post.getImages()) {
                            htmlPost += "<div class=\"feed_body_imgBox\" >\n" +
                                    "<a class=\"feed_body_imgLink\">\n" +
                                    "<img alt=\"#\" class=\"feed_body_img\" src=\"" + image.getUrl()+
                                    "\"></a>\n" +
                                    "</div>\n";
                        }
                        htmlPost += "  </div>\n" +
                                "                                       </div>\n" +
                                "                                    </div>\n" +
                                "                                    <div class=\"home_feed_footer\">\n" +
                                "                                       <div class=\"feed_footer_cont\">\n" +
                                "                                          <div class=\"feed_footer_actionBox\">\n" +
                                "                                             <div class=\"feed_footer_interactive\">\n" +
                                "                                                <div class=\"feed_footer_likeBox def_size\">\n" +
                                "                                                   <span class=\"like-icon\"><i class=\"" + (post.getLikeYN() == 1 ?  "fa-solid fa-heart\"" : "fa-regular fa-heart\"") +" data-post-id = \""+post.getId()+"\"" + "\"></i></span>\n" +
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
                                "                                                   <span class=\"feed_footer_commentNum\">"+post.getCommentSize()+"</span>개 모두 보기\n" +
                                "                                                </span>\n" +
                                "                                             </div>\n" +
                                "                                          </div>\n" +
                                "                                          <div class=\"feed_footer_inputCont\">\n" +
                                "                                             <form id=\""+post.getId()+"\">\n" +
                                "                                                <div class=\"feed_footer_inputBox\">\n" +
                                "                                                   <div class=\"feed_footer_postCommentBox\">\n" +
                                "                                                      <input type=\"text\" class=\"feed_footer_postComment\" placeholder=\"댓글 달기..\">\n" +
                                "                                                      <input type=\"hidden\" id = \"postId\" value=\""+post.getId()+"\">\n" +
                                "                                                   </div>\n" +
                                "                                                   <div class=\"feed_footer_emoteButton\">\n" +
                                "                                                      <span><i class=\"fa-regular fa-face-smile\"></i></span>\n" +
                                "                                                   </div>\n" +
                                "                                                </div>\n" +
                                "                                             </form>\n" +
                                "                                          </div>\n" +
                                "                                       </div>\n" +
                                "                                    </div>\n" +
                                "                                 </div>\n" +
                                "                              </article>";
                        htmlsPost += htmlPost;
                        htmlPost = "";



                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                response.put("htmlbookmark", htmlsPost); // 저장됨 탭
                htmlsPost = "";
                htmlPost = "";
                try {
                    postAllDTO = profileDetailService.getPosts(user.getUsername());
                    for (PostAllDTO post: postAllDTO) {
                        htmlPost = " <article id=\""+"Post"+post.getId()+"\">\n" +
                                "                                 <div class=\"home_feed_contents\">\n" +
                                "                                    <div class=\"home_feed_head\">\n" +
                                "                                       <div class=\"feed_head_box\">\n" +
                                "                                          <div class=\"feed_head_img_box1\">\n" +
                                "                                             <div>\n" +
                                "                                                <div class=\"feed_head_img_box2\">\n" +
                                "                                                   <a href=\"#\" class=\"feed_head_img\">\n" +
                                "                                                      <img alt=\"프로필 사진\" class=\"feed_head_profile_img\" src=\""+post.getUrl()+"\">\n" +
                                "                                                   </a>\n" +
                                "                                                </div>\n" +
                                "                                             </div>\n" +
                                "                                          </div>\n" +
                                "                                          <div class=\"feed_head_text_box1\">\n" +
                                "                                             <div class=\"feed_head_text_box2\">\n" +
                                "                                                <div>\n" +
                                "                                                   <div class=\"feed_head_nameInfo\">\n" +
                                "                                                      <a href=\"#\" class=\"feed_head_nameLink\">\n" +
                                "                                                         <span class=\"feed_head_name\">"+post.getName()+"</span>\n" +
                                "                                                      </a>\n" +
                                "                                                   </div>\n" +
                                "                                                </div>\n" +
                                "                                                <div class=\"feed_head_timeInfo\">\n" +
                                "                                                   <span class=\"dot_for_separate\">\n" +
                                "                                                      <span class=\"common_dot\">•</span>\n" +
                                "                                                   </span>\n" +
                                "                                                   <div class=\"feed_head_time_box\">\n" +
                                "                                                      <a class=\"feed_head_timeLink\">\n" +
                                "                                                         <span class=\"feed_head_time\">"+post.getDate()+"</span>\n" +
                                "                                                      </a>\n" +
                                "                                                   </div>\n" +
                                "                                                </div>\n" +
                                "                                             </div>\n" +
                                "                                          </div>\n" +
                                "                                          <div class=\"feed_head_more_box1\">\n" +
                                "                                             <div class=\"feed_head_more_box2\">\n" +
                                "                                                <div class=\"feed_head_morePopup\">\n" +
                                "                                                   <i class=\"fa-solid fa-ellipsis\"></i>\n" +
                                "                                                </div>\n" +
                                "                                             </div>\n" +
                                "                                          </div>\n" +
                                "                                       </div>\n" +
                                "                                    </div>\n" +
                                "                                    <div class=\"home_feed_body\">\n" +
                                "                                       <div class=\"feed_body_cont\">\n" +
                                "                                          <div class=\"feed_body_list\">";
                        for (PostImageDTO image:post.getImages()) {
                            htmlPost += "<div class=\"feed_body_imgBox\" >\n" +
                                    "<a class=\"feed_body_imgLink\">\n" +
                                    "<img alt=\"#\" class=\"feed_body_img\" src=\"" + image.getUrl()+
                                    "\"></a>\n" +
                                    "</div>\n";
                        }
                        htmlPost += "  </div>\n" +
                                "                                       </div>\n" +
                                "                                    </div>\n" +
                                "                                    <div class=\"home_feed_footer\">\n" +
                                "                                       <div class=\"feed_footer_cont\">\n" +
                                "                                          <div class=\"feed_footer_actionBox\">\n" +
                                "                                             <div class=\"feed_footer_interactive\">\n" +
                                "                                                <div class=\"feed_footer_likeBox def_size\">\n" +
                                "                                                   <span class=\"like-icon\"><i class=\"" + (post.getLikeYN() == 1 ?  "fa-solid fa-heart\"" : "fa-regular fa-heart\"") +" data-post-id = \""+post.getId()+"\"" + "\"></i></span>\n" +
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
                                "                                                   <span class=\"feed_footer_commentNum\">"+post.getCommentSize()+"</span>개 모두 보기\n" +
                                "                                                </span>\n" +
                                "                                             </div>\n" +
                                "                                          </div>\n" +
                                "                                          <div class=\"feed_footer_inputCont\">\n" +
                                "                                             <form id=\""+post.getId()+"\">\n" +
                                "                                                <div class=\"feed_footer_inputBox\">\n" +
                                "                                                   <div class=\"feed_footer_postCommentBox\">\n" +
                                "                                                      <input type=\"text\" class=\"feed_footer_postComment\" placeholder=\"댓글 달기..\">\n" +
                                "                                                      <input type=\"hidden\" id = \"postId\" value=\""+post.getId()+"\">\n" +
                                "                                                   </div>\n" +
                                "                                                   <div class=\"feed_footer_emoteButton\">\n" +
                                "                                                      <span><i class=\"fa-regular fa-face-smile\"></i></span>\n" +
                                "                                                   </div>\n" +
                                "                                                </div>\n" +
                                "                                             </form>\n" +
                                "                                          </div>\n" +
                                "                                       </div>\n" +
                                "                                    </div>\n" +
                                "                                 </div>\n" +
                                "                              </article>";
                        htmlsPost += htmlPost;
                        htmlPost = "";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                response.put("htmlpost", htmlsPost); // 게시물 탭
            }

            else {
                response.put("success", false);
                response.put("message", "북마크 등록 실패.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/registerLikeProfile")
    public ResponseEntity<Map<String, Object>> registerLike(@AuthenticationPrincipal UserDetails user, @RequestParam String postId) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (postService.registerLike(user.getUsername(), postId)) {
                response.put("count",pageService.getPostLikes(Long.valueOf(postId)).size());
                response.put("success", true);
                response.put("message", "좋아요 완료.");

                String htmlsPost = "";
                String htmlPost = "";
                try {
                    postAllBookmarkDTO = profileDetailService.getPostsBookmark(user.getUsername());
                    for (PostAllDTO post: postAllBookmarkDTO) {
                        htmlPost = " <article id=\""+"Post"+post.getId()+"\">\n" +
                                "                                 <div class=\"home_feed_contents\">\n" +
                                "                                    <div class=\"home_feed_head\">\n" +
                                "                                       <div class=\"feed_head_box\">\n" +
                                "                                          <div class=\"feed_head_img_box1\">\n" +
                                "                                             <div>\n" +
                                "                                                <div class=\"feed_head_img_box2\">\n" +
                                "                                                   <a href=\"#\" class=\"feed_head_img\">\n" +
                                "                                                      <img alt=\"프로필 사진\" class=\"feed_head_profile_img\" src=\""+post.getUrl()+"\">\n" +
                                "                                                   </a>\n" +
                                "                                                </div>\n" +
                                "                                             </div>\n" +
                                "                                          </div>\n" +
                                "                                          <div class=\"feed_head_text_box1\">\n" +
                                "                                             <div class=\"feed_head_text_box2\">\n" +
                                "                                                <div>\n" +
                                "                                                   <div class=\"feed_head_nameInfo\">\n" +
                                "                                                      <a href=\"#\" class=\"feed_head_nameLink\">\n" +
                                "                                                         <span class=\"feed_head_name\">"+post.getName()+"</span>\n" +
                                "                                                      </a>\n" +
                                "                                                   </div>\n" +
                                "                                                </div>\n" +
                                "                                                <div class=\"feed_head_timeInfo\">\n" +
                                "                                                   <span class=\"dot_for_separate\">\n" +
                                "                                                      <span class=\"common_dot\">•</span>\n" +
                                "                                                   </span>\n" +
                                "                                                   <div class=\"feed_head_time_box\">\n" +
                                "                                                      <a class=\"feed_head_timeLink\">\n" +
                                "                                                         <span class=\"feed_head_time\">"+post.getDate()+"</span>\n" +
                                "                                                      </a>\n" +
                                "                                                   </div>\n" +
                                "                                                </div>\n" +
                                "                                             </div>\n" +
                                "                                          </div>\n" +
                                "                                          <div class=\"feed_head_more_box1\">\n" +
                                "                                             <div class=\"feed_head_more_box2\">\n" +
                                "                                                <div class=\"feed_head_morePopup\">\n" +
                                "                                                   <i class=\"fa-solid fa-ellipsis\"></i>\n" +
                                "                                                </div>\n" +
                                "                                             </div>\n" +
                                "                                          </div>\n" +
                                "                                       </div>\n" +
                                "                                    </div>\n" +
                                "                                    <div class=\"home_feed_body\">\n" +
                                "                                       <div class=\"feed_body_cont\">\n" +
                                "                                          <div class=\"feed_body_list\">";
                        for (PostImageDTO image:post.getImages()) {
                            htmlPost += "<div class=\"feed_body_imgBox\" >\n" +
                                    "<a class=\"feed_body_imgLink\">\n" +
                                    "<img alt=\"#\" class=\"feed_body_img\" src=\"" + image.getUrl()+
                                    "\"></a>\n" +
                                    "</div>\n";
                        }
                        htmlPost += "  </div>\n" +
                                "                                       </div>\n" +
                                "                                    </div>\n" +
                                "                                    <div class=\"home_feed_footer\">\n" +
                                "                                       <div class=\"feed_footer_cont\">\n" +
                                "                                          <div class=\"feed_footer_actionBox\">\n" +
                                "                                             <div class=\"feed_footer_interactive\">\n" +
                                "                                                <div class=\"feed_footer_likeBox def_size\">\n" +
                                "                                                   <span class=\"like-icon\"><i class=\"" + (post.getLikeYN() == 1 ?  "fa-solid fa-heart\"" : "fa-regular fa-heart\"") +" data-post-id = \""+post.getId()+"\"" + "\"></i></span>\n" +
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
                                "                                                   <span class=\"feed_footer_commentNum\">"+post.getCommentSize()+"</span>개 모두 보기\n" +
                                "                                                </span>\n" +
                                "                                             </div>\n" +
                                "                                          </div>\n" +
                                "                                          <div class=\"feed_footer_inputCont\">\n" +
                                "                                             <form id=\""+post.getId()+"\">\n" +
                                "                                                <div class=\"feed_footer_inputBox\">\n" +
                                "                                                   <div class=\"feed_footer_postCommentBox\">\n" +
                                "                                                      <input type=\"text\" class=\"feed_footer_postComment\" placeholder=\"댓글 달기..\">\n" +
                                "                                                      <input type=\"hidden\" id = \"postId\" value=\""+post.getId()+"\">\n" +
                                "                                                   </div>\n" +
                                "                                                   <div class=\"feed_footer_emoteButton\">\n" +
                                "                                                      <span><i class=\"fa-regular fa-face-smile\"></i></span>\n" +
                                "                                                   </div>\n" +
                                "                                                </div>\n" +
                                "                                             </form>\n" +
                                "                                          </div>\n" +
                                "                                       </div>\n" +
                                "                                    </div>\n" +
                                "                                 </div>\n" +
                                "                              </article>";
                        htmlsPost += htmlPost;
                        htmlPost = "";



                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                response.put("htmlbookmark", htmlsPost); // 저장됨 탭
                htmlsPost = "";
                htmlPost = "";
                try {
                    postAllDTO = profileDetailService.getPosts(user.getUsername());
                    for (PostAllDTO post: postAllDTO) {
                        htmlPost = " <article id=\""+"Post"+post.getId()+"\">\n" +
                                "                                 <div class=\"home_feed_contents\">\n" +
                                "                                    <div class=\"home_feed_head\">\n" +
                                "                                       <div class=\"feed_head_box\">\n" +
                                "                                          <div class=\"feed_head_img_box1\">\n" +
                                "                                             <div>\n" +
                                "                                                <div class=\"feed_head_img_box2\">\n" +
                                "                                                   <a href=\"#\" class=\"feed_head_img\">\n" +
                                "                                                      <img alt=\"프로필 사진\" class=\"feed_head_profile_img\" src=\""+post.getUrl()+"\">\n" +
                                "                                                   </a>\n" +
                                "                                                </div>\n" +
                                "                                             </div>\n" +
                                "                                          </div>\n" +
                                "                                          <div class=\"feed_head_text_box1\">\n" +
                                "                                             <div class=\"feed_head_text_box2\">\n" +
                                "                                                <div>\n" +
                                "                                                   <div class=\"feed_head_nameInfo\">\n" +
                                "                                                      <a href=\"#\" class=\"feed_head_nameLink\">\n" +
                                "                                                         <span class=\"feed_head_name\">"+post.getName()+"</span>\n" +
                                "                                                      </a>\n" +
                                "                                                   </div>\n" +
                                "                                                </div>\n" +
                                "                                                <div class=\"feed_head_timeInfo\">\n" +
                                "                                                   <span class=\"dot_for_separate\">\n" +
                                "                                                      <span class=\"common_dot\">•</span>\n" +
                                "                                                   </span>\n" +
                                "                                                   <div class=\"feed_head_time_box\">\n" +
                                "                                                      <a class=\"feed_head_timeLink\">\n" +
                                "                                                         <span class=\"feed_head_time\">"+post.getDate()+"</span>\n" +
                                "                                                      </a>\n" +
                                "                                                   </div>\n" +
                                "                                                </div>\n" +
                                "                                             </div>\n" +
                                "                                          </div>\n" +
                                "                                          <div class=\"feed_head_more_box1\">\n" +
                                "                                             <div class=\"feed_head_more_box2\">\n" +
                                "                                                <div class=\"feed_head_morePopup\">\n" +
                                "                                                   <i class=\"fa-solid fa-ellipsis\"></i>\n" +
                                "                                                </div>\n" +
                                "                                             </div>\n" +
                                "                                          </div>\n" +
                                "                                       </div>\n" +
                                "                                    </div>\n" +
                                "                                    <div class=\"home_feed_body\">\n" +
                                "                                       <div class=\"feed_body_cont\">\n" +
                                "                                          <div class=\"feed_body_list\">";
                        for (PostImageDTO image:post.getImages()) {
                            htmlPost += "<div class=\"feed_body_imgBox\" >\n" +
                                    "<a class=\"feed_body_imgLink\">\n" +
                                    "<img alt=\"#\" class=\"feed_body_img\" src=\"" + image.getUrl()+
                                    "\"></a>\n" +
                                    "</div>\n";
                        }
                        htmlPost += "  </div>\n" +
                                "                                       </div>\n" +
                                "                                    </div>\n" +
                                "                                    <div class=\"home_feed_footer\">\n" +
                                "                                       <div class=\"feed_footer_cont\">\n" +
                                "                                          <div class=\"feed_footer_actionBox\">\n" +
                                "                                             <div class=\"feed_footer_interactive\">\n" +
                                "                                                <div class=\"feed_footer_likeBox def_size\">\n" +
                                "                                                   <span class=\"like-icon\"><i class=\"" + (post.getLikeYN() == 1 ?  "fa-solid fa-heart\"" : "fa-regular fa-heart\"") +" data-post-id = \""+post.getId()+"\"" + "\"></i></span>\n" +
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
                                "                                                   <span class=\"feed_footer_commentNum\">"+post.getCommentSize()+"</span>개 모두 보기\n" +
                                "                                                </span>\n" +
                                "                                             </div>\n" +
                                "                                          </div>\n" +
                                "                                          <div class=\"feed_footer_inputCont\">\n" +
                                "                                             <form id=\""+post.getId()+"\">\n" +
                                "                                                <div class=\"feed_footer_inputBox\">\n" +
                                "                                                   <div class=\"feed_footer_postCommentBox\">\n" +
                                "                                                      <input type=\"text\" class=\"feed_footer_postComment\" placeholder=\"댓글 달기..\">\n" +
                                "                                                      <input type=\"hidden\" id = \"postId\" value=\""+post.getId()+"\">\n" +
                                "                                                   </div>\n" +
                                "                                                   <div class=\"feed_footer_emoteButton\">\n" +
                                "                                                      <span><i class=\"fa-regular fa-face-smile\"></i></span>\n" +
                                "                                                   </div>\n" +
                                "                                                </div>\n" +
                                "                                             </form>\n" +
                                "                                          </div>\n" +
                                "                                       </div>\n" +
                                "                                    </div>\n" +
                                "                                 </div>\n" +
                                "                              </article>";
                        htmlsPost += htmlPost;
                        htmlPost = "";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                response.put("htmlpost", htmlsPost); // 게시물 탭


            } else {
                response.put("success", false);
                response.put("message", "좋아요 실패.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cancelLikeProfile")
    public ResponseEntity<Map<String, Object>> cancelLike(@AuthenticationPrincipal UserDetails user, @RequestParam String postId) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (postService.cancelLike(user.getUsername(), postId)) {
                response.put("count",pageService.getPostLikes(Long.valueOf(postId)).size());
                response.put("success", true);
                response.put("message", "좋아요 취소 완료.");


                String htmlsPost = "";
                String htmlPost = "";
                try {
                    postAllBookmarkDTO = profileDetailService.getPostsBookmark(user.getUsername());
                    for (PostAllDTO post: postAllBookmarkDTO) {
                        htmlPost = " <article id=\""+"Post"+post.getId()+"\">\n" +
                                "                                 <div class=\"home_feed_contents\">\n" +
                                "                                    <div class=\"home_feed_head\">\n" +
                                "                                       <div class=\"feed_head_box\">\n" +
                                "                                          <div class=\"feed_head_img_box1\">\n" +
                                "                                             <div>\n" +
                                "                                                <div class=\"feed_head_img_box2\">\n" +
                                "                                                   <a href=\"#\" class=\"feed_head_img\">\n" +
                                "                                                      <img alt=\"프로필 사진\" class=\"feed_head_profile_img\" src=\""+post.getUrl()+"\">\n" +
                                "                                                   </a>\n" +
                                "                                                </div>\n" +
                                "                                             </div>\n" +
                                "                                          </div>\n" +
                                "                                          <div class=\"feed_head_text_box1\">\n" +
                                "                                             <div class=\"feed_head_text_box2\">\n" +
                                "                                                <div>\n" +
                                "                                                   <div class=\"feed_head_nameInfo\">\n" +
                                "                                                      <a href=\"#\" class=\"feed_head_nameLink\">\n" +
                                "                                                         <span class=\"feed_head_name\">"+post.getName()+"</span>\n" +
                                "                                                      </a>\n" +
                                "                                                   </div>\n" +
                                "                                                </div>\n" +
                                "                                                <div class=\"feed_head_timeInfo\">\n" +
                                "                                                   <span class=\"dot_for_separate\">\n" +
                                "                                                      <span class=\"common_dot\">•</span>\n" +
                                "                                                   </span>\n" +
                                "                                                   <div class=\"feed_head_time_box\">\n" +
                                "                                                      <a class=\"feed_head_timeLink\">\n" +
                                "                                                         <span class=\"feed_head_time\">"+post.getDate()+"</span>\n" +
                                "                                                      </a>\n" +
                                "                                                   </div>\n" +
                                "                                                </div>\n" +
                                "                                             </div>\n" +
                                "                                          </div>\n" +
                                "                                          <div class=\"feed_head_more_box1\">\n" +
                                "                                             <div class=\"feed_head_more_box2\">\n" +
                                "                                                <div class=\"feed_head_morePopup\">\n" +
                                "                                                   <i class=\"fa-solid fa-ellipsis\"></i>\n" +
                                "                                                </div>\n" +
                                "                                             </div>\n" +
                                "                                          </div>\n" +
                                "                                       </div>\n" +
                                "                                    </div>\n" +
                                "                                    <div class=\"home_feed_body\">\n" +
                                "                                       <div class=\"feed_body_cont\">\n" +
                                "                                          <div class=\"feed_body_list\">";
                        for (PostImageDTO image:post.getImages()) {
                            htmlPost += "<div class=\"feed_body_imgBox\" >\n" +
                                    "<a class=\"feed_body_imgLink\">\n" +
                                    "<img alt=\"#\" class=\"feed_body_img\" src=\"" + image.getUrl()+
                                    "\"></a>\n" +
                                    "</div>\n";
                        }
                        htmlPost += "  </div>\n" +
                                "                                       </div>\n" +
                                "                                    </div>\n" +
                                "                                    <div class=\"home_feed_footer\">\n" +
                                "                                       <div class=\"feed_footer_cont\">\n" +
                                "                                          <div class=\"feed_footer_actionBox\">\n" +
                                "                                             <div class=\"feed_footer_interactive\">\n" +
                                "                                                <div class=\"feed_footer_likeBox def_size\">\n" +
                                "                                                   <span class=\"like-icon\"><i class=\"" + (post.getLikeYN() == 1 ?  "fa-solid fa-heart\"" : "fa-regular fa-heart\"") +" data-post-id = \""+post.getId()+"\"" + "\"></i></span>\n" +
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
                                "                                                   <span class=\"feed_footer_commentNum\">"+post.getCommentSize()+"</span>개 모두 보기\n" +
                                "                                                </span>\n" +
                                "                                             </div>\n" +
                                "                                          </div>\n" +
                                "                                          <div class=\"feed_footer_inputCont\">\n" +
                                "                                             <form id=\""+post.getId()+"\">\n" +
                                "                                                <div class=\"feed_footer_inputBox\">\n" +
                                "                                                   <div class=\"feed_footer_postCommentBox\">\n" +
                                "                                                      <input type=\"text\" class=\"feed_footer_postComment\" placeholder=\"댓글 달기..\">\n" +
                                "                                                      <input type=\"hidden\" id = \"postId\" value=\""+post.getId()+"\">\n" +
                                "                                                   </div>\n" +
                                "                                                   <div class=\"feed_footer_emoteButton\">\n" +
                                "                                                      <span><i class=\"fa-regular fa-face-smile\"></i></span>\n" +
                                "                                                   </div>\n" +
                                "                                                </div>\n" +
                                "                                             </form>\n" +
                                "                                          </div>\n" +
                                "                                       </div>\n" +
                                "                                    </div>\n" +
                                "                                 </div>\n" +
                                "                              </article>";
                        htmlsPost += htmlPost;
                        htmlPost = "";



                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                response.put("htmlbookmark", htmlsPost); // 저장됨 탭
                htmlsPost = "";
                htmlPost = "";
                try {
                    postAllDTO = profileDetailService.getPosts(user.getUsername());
                    for (PostAllDTO post: postAllDTO) {
                        htmlPost = " <article id=\""+"Post"+post.getId()+"\">\n" +
                                "                                 <div class=\"home_feed_contents\">\n" +
                                "                                    <div class=\"home_feed_head\">\n" +
                                "                                       <div class=\"feed_head_box\">\n" +
                                "                                          <div class=\"feed_head_img_box1\">\n" +
                                "                                             <div>\n" +
                                "                                                <div class=\"feed_head_img_box2\">\n" +
                                "                                                   <a href=\"#\" class=\"feed_head_img\">\n" +
                                "                                                      <img alt=\"프로필 사진\" class=\"feed_head_profile_img\" src=\""+post.getUrl()+"\">\n" +
                                "                                                   </a>\n" +
                                "                                                </div>\n" +
                                "                                             </div>\n" +
                                "                                          </div>\n" +
                                "                                          <div class=\"feed_head_text_box1\">\n" +
                                "                                             <div class=\"feed_head_text_box2\">\n" +
                                "                                                <div>\n" +
                                "                                                   <div class=\"feed_head_nameInfo\">\n" +
                                "                                                      <a href=\"#\" class=\"feed_head_nameLink\">\n" +
                                "                                                         <span class=\"feed_head_name\">"+post.getName()+"</span>\n" +
                                "                                                      </a>\n" +
                                "                                                   </div>\n" +
                                "                                                </div>\n" +
                                "                                                <div class=\"feed_head_timeInfo\">\n" +
                                "                                                   <span class=\"dot_for_separate\">\n" +
                                "                                                      <span class=\"common_dot\">•</span>\n" +
                                "                                                   </span>\n" +
                                "                                                   <div class=\"feed_head_time_box\">\n" +
                                "                                                      <a class=\"feed_head_timeLink\">\n" +
                                "                                                         <span class=\"feed_head_time\">"+post.getDate()+"</span>\n" +
                                "                                                      </a>\n" +
                                "                                                   </div>\n" +
                                "                                                </div>\n" +
                                "                                             </div>\n" +
                                "                                          </div>\n" +
                                "                                          <div class=\"feed_head_more_box1\">\n" +
                                "                                             <div class=\"feed_head_more_box2\">\n" +
                                "                                                <div class=\"feed_head_morePopup\">\n" +
                                "                                                   <i class=\"fa-solid fa-ellipsis\"></i>\n" +
                                "                                                </div>\n" +
                                "                                             </div>\n" +
                                "                                          </div>\n" +
                                "                                       </div>\n" +
                                "                                    </div>\n" +
                                "                                    <div class=\"home_feed_body\">\n" +
                                "                                       <div class=\"feed_body_cont\">\n" +
                                "                                          <div class=\"feed_body_list\">";
                        for (PostImageDTO image:post.getImages()) {
                            htmlPost += "<div class=\"feed_body_imgBox\" >\n" +
                                    "<a class=\"feed_body_imgLink\">\n" +
                                    "<img alt=\"#\" class=\"feed_body_img\" src=\"" + image.getUrl()+
                                    "\"></a>\n" +
                                    "</div>\n";
                        }
                        htmlPost += "  </div>\n" +
                                "                                       </div>\n" +
                                "                                    </div>\n" +
                                "                                    <div class=\"home_feed_footer\">\n" +
                                "                                       <div class=\"feed_footer_cont\">\n" +
                                "                                          <div class=\"feed_footer_actionBox\">\n" +
                                "                                             <div class=\"feed_footer_interactive\">\n" +
                                "                                                <div class=\"feed_footer_likeBox def_size\">\n" +
                                "                                                   <span class=\"like-icon\"><i class=\"" + (post.getLikeYN() == 1 ?  "fa-solid fa-heart\"" : "fa-regular fa-heart\"") +" data-post-id = \""+post.getId()+"\"" + "\"></i></span>\n" +
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
                                "                                                   <span class=\"feed_footer_commentNum\">"+post.getCommentSize()+"</span>개 모두 보기\n" +
                                "                                                </span>\n" +
                                "                                             </div>\n" +
                                "                                          </div>\n" +
                                "                                          <div class=\"feed_footer_inputCont\">\n" +
                                "                                             <form id=\""+post.getId()+"\">\n" +
                                "                                                <div class=\"feed_footer_inputBox\">\n" +
                                "                                                   <div class=\"feed_footer_postCommentBox\">\n" +
                                "                                                      <input type=\"text\" class=\"feed_footer_postComment\" placeholder=\"댓글 달기..\">\n" +
                                "                                                      <input type=\"hidden\" id = \"postId\" value=\""+post.getId()+"\">\n" +
                                "                                                   </div>\n" +
                                "                                                   <div class=\"feed_footer_emoteButton\">\n" +
                                "                                                      <span><i class=\"fa-regular fa-face-smile\"></i></span>\n" +
                                "                                                   </div>\n" +
                                "                                                </div>\n" +
                                "                                             </form>\n" +
                                "                                          </div>\n" +
                                "                                       </div>\n" +
                                "                                    </div>\n" +
                                "                                 </div>\n" +
                                "                              </article>";
                        htmlsPost += htmlPost;
                        htmlPost = "";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                response.put("htmlpost", htmlsPost); // 게시물 탭


            } else {
                response.put("success", false);
                response.put("message", "좋아요 취소 실패.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(response);
    }
}
