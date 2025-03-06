package tw.com.lyls.AppleJuice.controller.v2;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.map.MapUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.lyls.AppleJuice.domain.mysql.VwMenuRole;
import tw.com.lyls.AppleJuice.repository.mysql.VwMenuRoleRepository;
import tw.com.lyls.AppleJuice.service.vm.RespVM;
import tw.com.lyls.AppleJuice.util.SecurityUtils;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v2/menu")
@Tag(name = "Menu", description = "菜單相關 API")
public class MenuController {

    @Autowired
    private VwMenuRoleRepository vwMenuRoleRepository;

    @GetMapping
    public ResponseEntity<RespVM> getMenu() {
        // 取得當前使用者的角色。
        List<String> currentUserRoles = SecurityUtils.getCurrentUserRoles();
        // 根據角色取得菜單資料。
        List<VwMenuRole> allByRoleNameIn = vwMenuRoleRepository.findAllByRoleNameIn(currentUserRoles);
        // 將資料轉換成樹狀結構。
        List<TreeNode<Long>> nodeList = CollUtil.newArrayList();
        for (VwMenuRole vwMenuRole : allByRoleNameIn) {
            nodeList.add(new TreeNode<>(vwMenuRole.getMenuId(), vwMenuRole.getMenuParentId(), vwMenuRole.getMenuName(), 0L));
        }
        // 生成樹狀結構。
        List<Tree<Long>> menuTree = TreeUtil.build(nodeList, 0L);
        // 封裝。
        Map<String, List<Tree<Long>>> resultMap = MapUtil.builder("menuTree", menuTree).build();
        return ResponseEntity.ok().body(new RespVM(resultMap));
    }

}
