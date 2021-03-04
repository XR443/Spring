package com.github.hardlolmaster.web;

import com.github.hardlolmaster.domain.Post;
import com.github.hardlolmaster.domain.User;
import com.github.hardlolmaster.repository.PostRepository;
import com.github.hardlolmaster.repository.UserRepository;
import com.github.hardlolmaster.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@Transactional
public class BlogController {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final MutableAclService aclService;

    @Autowired
    public BlogController(PostRepository postRepository, UserRepository userRepository, MutableAclService aclService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.aclService = aclService;
    }


    @PostMapping("/post/create")
    public Post createPost(@RequestBody Post post) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Principal principal = (Principal) auth.getPrincipal();
        User user = userRepository.findByUsername(principal.getUsername());
        post.setUser(user);
        Post save = postRepository.save(post);

        final Sid owner = new PrincipalSid(auth);
        final Sid admin = new GrantedAuthoritySid("ROLE_ADMIN");
        final ObjectIdentity oid = new ObjectIdentityImpl(Post.class, save.getId());
        final MutableAcl acl = aclService.createAcl(oid);
        acl.setOwner(owner);
        acl.insertAce(acl.getEntries().size(), BasePermission.READ, admin, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.ADMINISTRATION, admin, true);
        aclService.updateAcl(acl);

        return save;
    }

}
