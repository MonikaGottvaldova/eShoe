/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.issuetracker.pages.component.issue;

import com.issuetracker.dao.api.IssueDao;
import com.issuetracker.model.Issue;
import com.issuetracker.model.IssuesRelationship;
import com.issuetracker.pages.IssueDetail;
import java.util.List;
import javax.inject.Inject;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 *
 * @author mgottval
 */
public class IssueRelationsListView<I extends IssuesRelationship> extends Panel {

    private ListView<IssuesRelationship> relationsListView;
    private List<IssuesRelationship> issues;
    @Inject
    private IssueDao issueDao;
//    private Issue issueRelatesTo;

    public IssueRelationsListView(String id, IModel<List<IssuesRelationship>> issues) {
        super(id);
        relationsListView = new ListView<IssuesRelationship>("issues", issues) {
            @Override
            protected void populateItem(final ListItem<IssuesRelationship> item) {
                final IssuesRelationship issuesRelation = item.getModelObject();

                  //  issueRelatesTo = issueDao.getIssueById(issuesRelation.getRelatesToIssue().getIssueId());

                item.add(new Label("relationName", issuesRelation.getRelationshipType().toString()));
                item.add(new Link<Issue>("issueRelLink") {
                    @Override
                    public void onClick() {
                        PageParameters pageParameters = new PageParameters();
                        pageParameters.add("issue", issuesRelation.getRelatesToIssue().getIssueId());
                        setResponsePage(IssueDetail.class, pageParameters);
                    }
                }.add(new Label("issueName", issuesRelation.getRelatesToIssue().getName())));
                 
            }
        };
        add(relationsListView);
    }

    public List<IssuesRelationship> getIssues() {
        return issues;
    }

    public void setIssues(List<IssuesRelationship> issues) {
        this.issues = issues;
    }
}
