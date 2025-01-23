from sklearn.cluster import KMeans
from sklearn.preprocessing import StandardScaler
import numpy as np

class LearningPatternAnalyzer:
    """Analyzes student learning patterns using clustering techniques."""
    
    def __init__(self, n_clusters=5):
        """
        Initialize the analyzer with clustering parameters.
        
        Args:
            n_clusters (int): Number of learning style clusters to identify
        """
        self.scaler = StandardScaler()
        self.clusterer = KMeans(n_clusters=n_clusters, random_state=42)
        self.cluster_profiles = None
    
    def analyze_learning_patterns(self, student_interactions):
        """
        Analyze student learning patterns from interaction data.
        
        Args:
            student_interactions (array-like): Matrix of student interaction features
            
        Returns:
            dict: Analysis results including cluster assignment and recommendations
        """
        # Preprocess data
        scaled_data = self.scaler.fit_transform(student_interactions)
        
        # Cluster students based on learning patterns
        clusters = self.clusterer.fit_predict(scaled_data)
        
        # Generate cluster profiles
        self.cluster_profiles = self._generate_cluster_profiles(scaled_data, clusters)
        
        return {
            "learning_style_cluster": int(clusters[0]),
            "cluster_profile": self.cluster_profiles[clusters[0]],
            "peer_group": self.get_peer_group(clusters),
            "recommended_materials": self.get_recommendations(clusters[0])
        }
    
    def _generate_cluster_profiles(self, data, clusters):
        """Generate profiles describing characteristics of each cluster."""
        profiles = {}
        for i in range(self.clusterer.n_clusters):
            cluster_data = data[clusters == i]
            profiles[i] = {
                "size": len(cluster_data),
                "center": self.clusterer.cluster_centers_[i],
                "variance": np.var(cluster_data, axis=0)
            }
        return profiles
    
    def get_peer_group(self, clusters):
        """Identify peers in the same learning style cluster."""
        return [i for i, c in enumerate(clusters) if c == clusters[0]]
    
    def get_recommendations(self, cluster):
        """Generate personalized learning recommendations based on cluster."""
        # Placeholder - implement recommendation logic based on cluster profiles
        recommendations = {
            0: ["visual_learning_materials", "interactive_exercises"],
            1: ["text_based_materials", "practice_problems"],
            2: ["video_tutorials", "group_projects"],
            3: ["hands_on_workshops", "peer_learning"],
            4: ["self_paced_modules", "assessments"]
        }
        return recommendations.get(cluster, []) 