package org.roda.rodain.schema;

import java.util.List;

/**
 * @author Andre Pereira apereira@keep.pt
 * @since 22-09-2015.
 */
public class DescriptionObject {
  private String id;
  private String title;
  private String parentId;
  private String level;
  private String descriptionlevel;
  private List<DescriptionObject> children;

  public DescriptionObject() {
  }

  public DescriptionObject(String id, String title, String parentId, String level, String descriptionlevel,
    List<DescriptionObject> children) {
    this.id = id;
    this.title = title;
    this.parentId = parentId;
    this.level = level;
    this.descriptionlevel = descriptionlevel;
    this.children = children;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
  }

  public String getDescriptionlevel() {
    return descriptionlevel;
  }

  public void setDescriptionlevel(String descriptionlevel) {
    this.descriptionlevel = descriptionlevel;
  }

  public List<DescriptionObject> getChildren() {
    return children;
  }

  public void setChildren(List<DescriptionObject> children) {
    this.children = children;
  }
}
